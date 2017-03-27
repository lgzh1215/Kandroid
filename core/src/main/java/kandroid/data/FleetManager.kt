package kandroid.data

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kandroid.observer.kcsapi.*
import kandroid.utils.CatException
import kandroid.utils.collection.IDDictionary
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
    operator fun get(fleetID: Int): FleetData {
        return fleetDatas[fleetID]
    }

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        when (apiName) {
            api_port.port.name,
            api_get_member.deck.name,
            api_get_member.ship2.name,
            api_get_member.ship3.name,
            api_get_member.ship_deck.name,
            api_req_hensei.preset_select.name,
            api_req_kaisou.powerup.name -> {
                when (responseData) {
                    is JsonArray -> {
                        for (elem in responseData) {
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
                    is JsonObject -> {
                        val id = responseData["api_id"].int()
                        var fleet = fleetDatas[id]
                        if (fleet == null) {
                            fleet = FleetData()
                            fleet.loadFromResponse(apiName, responseData)
                            fleetDatas.put(fleet)
                        } else {
                            fleet.loadFromResponse(apiName, responseData)
                        }
                    }
                    else -> throw CatException()
                }
            }
            api_get_member.ndock.name -> {

                fleetDatas.forEach { it.loadFromResponse(apiName, responseData) }
            }
            else -> throw CatException()
        }
    }

    override fun loadFromRequest(apiName: String, requestData: MutableMap<String, String>) {
        when (apiName) {
            api_req_nyukyo.start.name,
            api_req_nyukyo.speedchange.name,
            api_req_kaisou.remodeling.name -> {
                fleetDatas.forEach { it.loadFromRequest(apiName, requestData) }
            }
            api_req_hensei.change.name -> {
                val index = requestData["api_ship_idx"]!!.toInt() // 舰队里第几格 0/1/2/3/4/5 , -1:旗舰外全解除
                if (index != -1) {
                    val fleetId = requestData["api_id"]!!.toInt() // 舰队id 1/2/3/4
                    val s = fleetDatas[fleetId]!![index].toString()
                    requestData.put("replaced_id", s)
                }
                for (fleet in fleetDatas) {
                    fleet.loadFromRequest(apiName, requestData)
                }
            }
            api_req_hensei.combined.name -> {
                combinedFlag = requestData["api_combined_type"]!!.toInt()
            }
            else -> throw CatException()
        }
    }

    fun startAnchorageRepairingTimer() {
        anchorageRepairingTimer = Date()
    }
}
