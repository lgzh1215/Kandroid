package kandroid.observer

import kandroid.observer.kcsapi.*
import kandroid.utils.coroutine.SingleThread
import kandroid.utils.log.Logger
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import java.lang.Exception
import java.util.*

object ApiLoader {

    fun load(rawData: RawData) = launch(SingleThread) {
        val data = run(CommonPool) {
            rawData.decode()
        }
        launch(CommonPool) {
            data.saveToFile()
        }
        val apiBase = apiHolder[data.uri]

        Logger.i("接收数据${apiBase?.name ?: "${rawData.uri}，但未处理"}")

        if (apiBase != null) {
            try {
                apiBase.onDataReceived(data)
            } catch (e: Exception) {

            }
        }
    }

    private val apiHolder = HashMap<String, ApiBase>()

    fun add(apiBase: ApiBase) {
        apiHolder.put(apiBase.name, apiBase)
    }

    init {
        add(api_start2)
        add(api_get_member.require_info)
        add(api_port.port)

        add(api_get_member.kdock)
        add(api_get_member.ndock)
        add(api_get_member.basic)
        add(api_get_member.questlist)
        add(api_get_member.deck)
        add(api_get_member.ship_deck)
        add(api_get_member.ship2)
        add(api_get_member.ship3)
        add(api_get_member.slot_item)
        add(api_get_member.useitem)
        add(api_get_member.material)
        add(api_get_member.mapinfo)

        add(api_req_air_corps.change_name)
        add(api_req_air_corps.set_plane)
        add(api_req_air_corps.set_action)
        add(api_req_air_corps.supply)
        add(api_req_air_corps.expand_base)

        add(api_req_hensei.change)
        add(api_req_hensei.combined)
        add(api_req_hensei.preset_select)

        add(api_req_hokyu.charge)

        add(api_req_kaisou.open_exslot)
        add(api_req_kaisou.powerup)
        add(api_req_kaisou.remodeling)
        add(api_req_kaisou.slot_deprive)
        add(api_req_kaisou.slot_exchange_index)

        add(api_req_kousyou.createitem)
        add(api_req_kousyou.createship_speedchange)
        add(api_req_kousyou.destroyitem2)
        add(api_req_kousyou.destroyship)
        add(api_req_kousyou.getship)
        add(api_req_kousyou.remodel_slot)

        add(api_req_member.get_practice_enemyinfo)
        add(api_req_member.updatecomment)
        add(api_req_member.updatedeckname)

        add(api_req_nyukyo.speedchange)
        add(api_req_nyukyo.start)

        add(api_req_quest.stop)
        add(api_req_quest.clearitemget)
    }
}