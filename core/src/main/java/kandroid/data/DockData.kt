package kandroid.data

import com.google.gson.JsonElement
import kandroid.observer.kcsapi.api_req_nyukyo
import kandroid.utils.CatException
import kandroid.utils.collection.Identifiable
import kandroid.utils.json.get
import kandroid.utils.json.int
import kandroid.utils.json.long
import kandroid.utils.json.set
import java.util.*

/**
 * 入渠
 */
class DockData : JsonWrapper(), RequestDataListener, Identifiable {

    val dockId: Int get() = data["api_id"].int()
    /**
     * 入渠状态
     * -1=未解锁，0=空，1=入渠中
     */
    val state: Int get() = data["api_state"].int()
    val shipId: Int get() = data["api_ship_id"].int()
    val completionTime: Date get() = Date(data["api_complete_time"].long())

    override val id: Int get() = dockId

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        val state = this.state
        val shipId = this.shipId
        super.loadFromResponse(apiName, responseData)
        val newState = this.state
        if (state == 1 && newState == 0 && shipId != 0) {
            KCDatabase.ships[shipId]?.repair()
        }
    }

    override fun loadFromRequest(apiName: String, requestData: MutableMap<String, String>) {
        when (apiName) {
            api_req_nyukyo.speedchange.name -> {
                if (state == 1 && shipId != 0) {
                    KCDatabase.ships[shipId]?.repair()
                    data["api_state"] = 0
                    data["api_ship_id"] = 0
                }
            }
            else -> CatException()
        }
    }
}