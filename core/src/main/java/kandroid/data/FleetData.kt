package kandroid.data

import com.google.gson.JsonElement
import kandroid.observer.kcsapi.api_get_member
import kandroid.observer.kcsapi.api_port
import kandroid.observer.kcsapi.api_req_kaisou
import kandroid.observer.kcsapi.api_req_nyukyo
import kandroid.utils.Identifiable
import kandroid.utils.json.*
import java.util.*

class FleetData : JsonWrapper(), RequestDataListener, Identifiable {

    val fleetID: Int get() = data["api_id"].int()
    val name: String get() = data["api_name"].string()
    val expeditionState: Int get() = data["api_mission"][0].int()
    val expeditionDestination: Int get() = data["api_mission"][1].int()
    val expeditionTime: Date get() = Date(data["api_mission"][2].long())
    val members: List<Int> get() = data["api_ship"].list()

    val membersInstance: List<ShipData> get() = members.mapNotNull { KCDatabase.ships[it] }
    val membersWithoutEscaped: List<ShipData> get() = members.mapNotNull {
        if (_escapedShipList.contains(it)) null else KCDatabase.ships[it]
    }

    private var _escapedShipList: ArrayList<Int> = ArrayList()
    val escapedShipList: List<Int> get() = _escapedShipList

    var isInSortie: Boolean = false
    var conditionTime: Date? = null
    var isConditionTimeLocked: Boolean = true

    operator fun get(index: Int): Int = members[index]

    override val id: Int get() = fleetID

    override fun loadFromRequest(apiName: String, requestData: Map<String, String>) {
        when (apiName) {
            api_req_kaisou.remodeling.name -> {
                if (members.contains(requestData["api_id"]?.toInt()))
                    SetConditionTimer()
            }
            api_req_nyukyo.start.name,
            api_req_nyukyo.speedchange.name
            -> {
                ShortenConditionTimer()
            }
        }
    }

    private fun SetConditionTimer() {
    }

    private fun ShortenConditionTimer() {
    }

    private fun UnlockConditionTimer() {

    }

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        when (apiName) {
            api_port.port.name -> {
                super.loadFromResponse(apiName, responseData)

                _escapedShipList.clear()
                if (isInSortie) {
                }
                isInSortie = false

                UnlockConditionTimer()
                ShortenConditionTimer()
            }
            api_get_member.ndock.name,
            "api_req_kousyou/destroyship",
            "api_get_member/ship3",
            "api_req_kaisou/powerup" -> ShortenConditionTimer()
        }
    }
    //TODO
}