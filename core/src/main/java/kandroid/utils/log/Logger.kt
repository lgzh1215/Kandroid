package kandroid.utils.log

import kandroid.config.Config
import kandroid.thread.Threads
import kandroid.utils.Listenable
import kandroid.utils.yyyyMMdd
import kandroid.utils.yyyyMMdd_HHmmssSSS
import org.apache.commons.io.FileUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.PrintStream
import java.util.*

object Logger : Listenable<kandroid.utils.log.Logger.LogData> {
    override val listeners = ArrayList<(LogData) -> Unit>()

    private val logger: Logger = LoggerFactory.getLogger("kandroid")

    private val logs = ArrayList<LogData>()

    data class LogData(val date: Date, val level: String, val message: String, val exception: Exception? = null) {
        override fun toString(): String = "[${date.yyyyMMdd_HHmmssSSS}][$level] : $message"
    }

    fun e(str: String, exception: Exception? = null) {
        addLog("ERROR", str)
        logger.error(str)
        exception?.printStackTrace()
    }

    fun d(str: String) {
        addLog("DEBUG", str)
        logger.debug(str)
    }

    fun i(str: String) {
        addLog("INFO", str)
        logger.info(str)
    }

    private fun addLog(level: String, str: String, exception: Exception? = null) {
        Threads.pool.execute {
            synchronized(Logger) {
                val logData = LogData(Date(), level, str, exception)

                listeners.forEach { it(logData) }

                logs.add(logData)
                if (logs.size > 20) {
                    saveToFile()
                }
            }
        }
    }

    fun saveToFile() {
        Threads.pool.execute {
            synchronized(Logger) {
                val file = Config.getSaveLogFile(Date().yyyyMMdd)
                val printStream = PrintStream(FileUtils.openOutputStream(file, true))

                printStream.use { ps ->
                    logs.forEach { logData ->
                        ps.println(logData.toString())
                        logData.exception?.printStackTrace(ps)
                        ps.println()
                    }
                }
                logs.clear()
            }
        }
    }
}