package kandroid.data

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import kandroid.newdata.RequestDataListener
import kandroid.newdata.ResponseDataListener
import kandroid.utils.IDDictionary
import kandroid.utils.json.get
import kandroid.utils.json.int
import java.util.*

class FleetManager : RequestDataListener, ResponseDataListener {

    var fleetDatas: IDDictionary<FleetData> = IDDictionary()

    var combinedFlag: Int = 0
    var anchorageRepairingTimer: Date = Date(0L)

    operator fun get(fleetID: Int): FleetData {
        return fleetDatas[fleetID]
    }

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        when (apiName) {
            "api_port/port" -> {
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
        }
    }

    override fun loadFromRequest(apiName: String, requestData: Map<String, String>) {
    }

    fun startAnchorageRepairingTimer() {
        anchorageRepairingTimer = Date()
    }
}
