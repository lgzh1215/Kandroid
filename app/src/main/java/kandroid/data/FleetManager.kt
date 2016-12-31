package kandroid.data

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import kandroid.observer.kcsapi.api_get_member
import kandroid.observer.kcsapi.api_port
import kandroid.observer.kcsapi.api_req_kaisou
import kandroid.observer.kcsapi.api_req_nyukyo
import kandroid.utils.CatException
import kandroid.utils.IDDictionary
import kandroid.utils.json.get
import kandroid.utils.json.int
import java.util.*

class FleetManager : RequestDataListener, ResponseDataListener {

    var fleetDatas: IDDictionary<FleetData> = IDDictionary()

    var combinedFlag: Int = 0
    var anchorageRepairingTimer: Date = Date(0L)

    /**
     * 舰队ID：1/2/3/4
     */
    operator fun get(fleetID: Int): FleetData? {
        return fleetDatas[fleetID]
    }

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        when (apiName) {
            api_port.port.name,
            api_get_member.deck.name,
            api_get_member.ship2.name,
            api_get_member.ship3.name,
            api_get_member.ship_deck.name -> {
                for (elem in responseData as JsonArray) {
                    val id = elem["api_id"].int()
                    var fleet = fleetDatas[id]
                    if (fleet == null) {
                        fleet = FleetData()
                        fleet.loadFromResponse(apiName, elem)
                        fleetDatas.put(fleet)
                    } else {
                        fleet.loadFromResponse(apiName, elem)
                    }
                }
            }
            api_get_member.ndock.name -> {
                fleetDatas.forEach { it.loadFromResponse(apiName, responseData) }
            }
            else -> CatException()
        }
    }

    override fun loadFromRequest(apiName: String, requestData: Map<String, String>) {
        when (apiName) {
            api_req_nyukyo.start.name,
            api_req_nyukyo.speedchange.name,
            api_req_kaisou.remodeling.name -> {
                fleetDatas.forEach { it.loadFromRequest(apiName, requestData) }
            }
            else -> throw CatException()
        }
    }

    fun startAnchorageRepairingTimer() {
        anchorageRepairingTimer = Date()
    }
}
