package kandroid.observer

import kandroid.config.Config
import kandroid.observer.kcsapi.api_get_member
import kandroid.observer.kcsapi.api_port
import kandroid.observer.kcsapi.api_start2
import kandroid.utils.log.Logger
import kandroid.utils.yyyyMMdd_HHmmssSSS
import org.apache.commons.io.FileUtils
import java.nio.charset.Charset
import java.util.*

object ApiLoader {

    private val lock = Any()

    fun load(rawData: RawData) {
        Logger.i("接收数据${rawData.uri}")
        synchronized(lock) {
            val data = rawData.decode()
            ApiLoader.save(data)
            val apiBase = ApiLoader[data.uri]
            apiBase?.onDataReceived(data)
            apiBase?.notifyListeners()
        }
    }

    fun save(rawData: RawData) {
        var date: String? = null
        try {
            if (Config.isSaveKcsRequest) {
                date = rawData.date.yyyyMMdd_HHmmssSSS
                val fileName = "${date}Q@${rawData.uri.replace('/', '@')}.txt"
                val file = Config.getSaveKcsApiFile(fileName)
                FileUtils.writeStringToFile(file, rawData.requestString, Charset.defaultCharset())
            }
        } catch (e: Exception) {
            Logger.e("保存Request失败 -> $e", e)
        }

        try {
            if (Config.isSaveKcsResponse) {
                if (date == null) date = rawData.date.yyyyMMdd_HHmmssSSS
                val fileName = "${date}S@${rawData.uri.replace('/', '@')}.json"
                val file = Config.getSaveKcsApiFile(fileName)
                FileUtils.writeStringToFile(file, rawData.responseString, Charset.defaultCharset())
            }
        } catch (e: Exception) {
            Logger.e("保存Response失败 -> $e", e)
        }
    }

    fun some(rawData: RawData) {
        val file = Config.getSaveUserDataFile(rawData.uri)
        FileUtils.writeStringToFile(file, rawData.responseString, Charset.defaultCharset())
    }

    private val apiHolder = HashMap<String, ApiBase>()

    operator fun get(apiName: String): ApiBase? {
        return apiHolder[apiName]
    }

    init {
        fun HashMap<String, ApiBase>.put(apiBase: ApiBase) {
            this.put(apiBase.name, apiBase)
        }
        apiHolder.put(api_start2)
        apiHolder.put(api_port.port)
        apiHolder.put(api_get_member.deck)
    }
}