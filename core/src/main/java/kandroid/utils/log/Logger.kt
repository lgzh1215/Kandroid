package kandroid.utils.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

object Logger {

    val logger: Logger = LoggerFactory.getLogger("kandroid")

    val listeners = ArrayList<(LogData) -> Unit>()

    val logs = ArrayList<LogData>()

    data class LogData(val date: Date, val level: String, val message: String)

    fun e(str: String) {
        addLog("ERROR", str)
        logger.error(str)
    }

    fun d(str: String) {
        addLog("DEBUG", str)
        logger.debug(str)
    }

    fun i(str: String) {
        addLog("INFO", str)
        logger.info(str)
    }

    fun t(str: String) {
        addLog("TRACE", str)
        logger.trace(str)
    }

    private fun addLog(level: String, str: String) {
        val logData = LogData(Date(), level, str)

        listeners.forEach { it(logData) }

        logs.add(logData)
        if (logs.size > 20) {
            //TODO save log
            logs.clear()
        }
    }
}