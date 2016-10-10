package kandroid.observer

import kandroid.config.Config
import kandroid.observer.kcsapi.*
import kandroid.utils.Utils
import org.apache.commons.io.FileUtils
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.printStackTrace
import kotlin.text.replace

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
                    val request = rawData.request
                    if (request != null) {
                        date = Utils.getDateString(rawData.date)
                        val fileName = "${date}Q@${rawData.uri.replace('/', '@')}.txt"
                        val file = Config.config.getSaveKcsApiFile(fileName)
                        org.apache.commons.io.FileUtils.writeStringToFile(file, String(request), Charset.defaultCharset())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            try {
                if (Config.config.isSaveKcsResponse) {
                    if (date == null) {
                        date = Utils.getDateString(rawData.date)
                    }
                    val fileName = "${date}S@${rawData.uri.replace('/', '@')}.json"
                    val file = Config.config.getSaveKcsApiFile(fileName)
                    org.apache.commons.io.FileUtils.writeStringToFile(file, rawData.toString(), Charset.defaultCharset())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun getApi(apiName: String): ApiBase? {
        return Holder[apiName]
    }

    private object Holder {
        private val APIS = HashMap<String, ApiBase>()

        fun put(apiBase: ApiBase) {
            APIS.put(apiBase.apiName, apiBase)
        }

        operator fun get(apiName: String): ApiBase? {
            return APIS[apiName]
        }
    }

    init {
        Holder.put(api_start2())

        Holder.put(api_get_member.require_info())
        Holder.put(api_get_member.slot_item())
        Holder.put(api_get_member.kdock())
        Holder.put(api_get_member.useitem())

        Holder.put(api_port.port())
        Holder.put(api_get_member.material())
        Holder.put(api_get_member.basic())
        Holder.put(api_get_member.ndock())
        Holder.put(api_get_member.deck())

        Holder.put(api_get_member.mapinfo())

        Holder.put(api_req_nyukyo.start())
        Holder.put(api_req_nyukyo.speedchange())

        Holder.put(api_req_mission.start())
        Holder.put(api_req_mission.result())

        Holder.put(api_get_member.ship3())
        Holder.put(api_get_member.ship2())

        Holder.put(api_get_member.ship_deck())
    }
}