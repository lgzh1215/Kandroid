package kandroid.observer

import kandroid.config.Config
import kandroid.observer.kcsapi.api_start2
import kandroid.utils.Utils
import org.apache.commons.io.FileUtils
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object ApiLoader {
    private var threadPool: ExecutorService? = null

    fun start() {
        stop()
        threadPool = Executors.newSingleThreadExecutor()
    }

    fun stop() {
        if (threadPool != null) {
            threadPool!!.shutdown()
            threadPool = null
        }
    }

    fun load(rawData: RawData) {
        if (threadPool != null && !threadPool!!.isShutdown) {
            threadPool!!.execute(rawData)
        }
    }

    fun save(rawData: RawData) {
        if (Config.config.isSaveKcsApi) {

            var date: String? = null
            try {
                if (Config.config.isSaveKcsRequest) {
                    date = Utils.getDateString(rawData.date)
                    val fileName = "${date}Q@${rawData.uri.replace('/', '@')}.txt"
                    val file = Config.config.getSaveKcsApiFile(fileName)
                    FileUtils.writeStringToFile(file, rawData.requestString, Charset.defaultCharset())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            try {
                if (Config.config.isSaveKcsResponse) {
                    if (date == null) date = Utils.getDateString(rawData.date)
                    val fileName = "${date}S@${rawData.uri.replace('/', '@')}.json"
                    val file = Config.config.getSaveKcsApiFile(fileName)
                    FileUtils.writeStringToFile(file, rawData.responseString, Charset.defaultCharset())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    val apiHolder = HashMap<String, ApiBase>()

    operator fun get(apiName: String): ApiBase? {
        return apiHolder[apiName]
    }

    init {
        apiHolder.put(api_start2.name, api_start2)
    }
}