package kandroid.observer

import kandroid.config.Config
import kandroid.observer.ApiLoader.get
import kandroid.observer.kcsapi.*
import kandroid.utils.coroutine.RawDataElement
import kandroid.utils.coroutine.SingleThread
import kandroid.utils.log.Logger
import kandroid.utils.yyyyMMdd_HHmmssSSS
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import org.apache.commons.io.FileUtils
import java.nio.charset.Charset
import java.util.*

object ApiLoader {

    private val lock = Any()

    fun load(rawData: RawData) {
        synchronized(lock) {
            val data = rawData.decode()
            ApiLoader.save(data)
            val apiBase = ApiLoader[data.uri]
            if (apiBase != null) {
                apiBase.onDataReceived(data)
                apiBase.notifyListeners()
            }
            Logger.i("接收数据${apiBase?.name ?: "${rawData.uri}，但未处理"}")
        }
    }
//
//    fun aload(rawData: RawData) = launch(SingleThread) {
//        runBlocking(CommonPool) {
//            rawData.decode()
//        }.also { data ->
//            ApiLoader[data.uri]?.
//        }
//    }


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
        apiHolder.put(api_get_member.require_info)
        apiHolder.put(api_port.port)

        apiHolder.put(api_get_member.kdock)
        apiHolder.put(api_get_member.ndock)
        apiHolder.put(api_get_member.basic)
        apiHolder.put(api_get_member.questlist)
        apiHolder.put(api_get_member.deck)
        apiHolder.put(api_get_member.ship_deck)
        apiHolder.put(api_get_member.ship2)
        apiHolder.put(api_get_member.ship3)
        apiHolder.put(api_get_member.slot_item)
        apiHolder.put(api_get_member.useitem)
        apiHolder.put(api_get_member.material)
        apiHolder.put(api_get_member.mapinfo)

        apiHolder.put(api_req_air_corps.change_name)
        apiHolder.put(api_req_air_corps.set_plane)
        apiHolder.put(api_req_air_corps.set_action)
        apiHolder.put(api_req_air_corps.supply)
        apiHolder.put(api_req_air_corps.expand_base)

        apiHolder.put(api_req_hensei.change)
        apiHolder.put(api_req_hensei.combined)
        apiHolder.put(api_req_hensei.preset_select)

        apiHolder.put(api_req_hokyu.charge)

        apiHolder.put(api_req_kaisou.open_exslot)
        apiHolder.put(api_req_kaisou.powerup)
        apiHolder.put(api_req_kaisou.remodeling)
        apiHolder.put(api_req_kaisou.slot_deprive)
        apiHolder.put(api_req_kaisou.slot_exchange_index)

        apiHolder.put(api_req_kousyou.createitem)
        apiHolder.put(api_req_kousyou.createship_speedchange)
        apiHolder.put(api_req_kousyou.destroyitem2)
        apiHolder.put(api_req_kousyou.destroyship)
        apiHolder.put(api_req_kousyou.getship)
        apiHolder.put(api_req_kousyou.remodel_slot)

        apiHolder.put(api_req_member.get_practice_enemyinfo)
        apiHolder.put(api_req_member.updatecomment)
        apiHolder.put(api_req_member.updatedeckname)

        apiHolder.put(api_req_nyukyo.speedchange)
        apiHolder.put(api_req_nyukyo.start)

        apiHolder.put(api_req_quest.stop)
        apiHolder.put(api_req_quest.clearitemget)
    }
}