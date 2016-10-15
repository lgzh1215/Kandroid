package kandroid.data

import com.google.gson.JsonElement
import kandroid.newdata.JsonWrapper
import kandroid.observer.POJO
import kandroid.observer.RawData
import kandroid.utils.Identifiable
import java.util.*

class FleetData : JsonWrapper(), Identifiable {

    val isInSortie: Boolean = false

    val fleetID: Int
        get() = data.api_id

    val name: String
        get() = data.api_name

    val expeditionState: Int
        get() = data.api_mission!![0]

    val expeditionDestination: Int
        get() = data.api_mission!![1]

    val expeditionTime: Date
        get() = Date(data.api_mission!![2].toLong())

    val members: List<Int>
        get() = data.api_ship

    val membersInstance: List<ShipData>
        get() = throw UnsupportedOperationException()

    val membersWithoutEscaped: List<ShipData>
        get() = throw UnsupportedOperationException()

    val escapedShipList: List<Int>
        get() = throw UnsupportedOperationException()

    val conditionTime: Date
        get() = throw UnsupportedOperationException()

    val isConditionTimeLocked: Boolean
        get() = throw UnsupportedOperationException()

    override val id: Int
        get() = data.api_id

    fun loadFromRequest(apiName: String, rawData: RawData) {
        when (apiName) {
            "api_req_nyukyo/start", "api_req_nyukyo/speedchange" -> ShortenConditionTimer()
        }
    }

    private fun ShortenConditionTimer() {
        //TODO
    }

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        when (apiName) {
            "api_get_member/ndock" -> ShortenConditionTimer()
        }
    }

    //TODO

    open class ApiDeck : POJO() {
        var api_member_id: Int = 0
        var api_id: Int = 0
        var api_name: String? = null
        var api_name_id: String? = null
        var api_flagship: String? = null
        var api_mission: List<Int>? = null
        var api_ship: List<Int>? = null
    }
}