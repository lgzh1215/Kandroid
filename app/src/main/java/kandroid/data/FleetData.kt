package kandroid.data

import com.google.gson.JsonElement
import kandroid.observer.kcsapi.*
import kandroid.utils.CatException
import kandroid.utils.Identifiable
import kandroid.utils.json.*
import java.util.*

class FleetData : JsonWrapper(), RequestDataListener, Identifiable {

    val NO_SHIP = -1
    /**
     * 舰队ID, 1/2/3/4
     */
    val fleetID: Int get() = data["api_id"].int()
    /**
     * 舰队名
     */
    val name: String get() = data["api_name"].string()
    /**
     * 遠征状態
     * 0=未出撃, 1=遠征中, 2=遠征帰投, 3=強制帰投中
     */
    val expeditionState: Int get() = data["api_mission"][0].int()
    /**
     * 遠征ID
     */
    val expeditionDestination: Int get() = data["api_mission"][1].int()
    /**
     * 遠征帰投時間
     */
    val expeditionTime: Date get() = Date(data["api_mission"][2].long())
    val members: List<Int> get() = data["api_ship"].list()

    val membersInstance: List<ShipData?> get() = members.map { KCDatabase.ships[it] }
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

    override fun loadFromRequest(apiName: String, requestData: MutableMap<String, String>) {
        when (apiName) {
            api_req_kaisou.remodeling.name -> {
                if (members.contains(requestData["api_id", 0]))
                    SetConditionTimer()
            }
            api_req_nyukyo.start.name,
            api_req_nyukyo.speedchange.name -> {
                ShortenConditionTimer()
            }
            api_req_hensei.change.name -> { // TODO 复查
                val fleetId = requestData["api_id"]!!.toInt() // 舰队id 1/2/3/4
                var index = requestData["api_ship_idx"]!!.toInt() // 舰队里第几格 0/1/2/3/4/5 , -1:旗舰外全解除
                val shipId = requestData["api_ship_id"]!!.toInt() // 编到index的舰娘id
                val replacedID = requestData["replaced_id"]!!.toInt()

                val members = members
                if (this.fleetID == fleetId) {
                    if (index == -1) {
                        // 旗舰以外全解除
                        for (i in 1..members.size) {
                            data["api_ship"][i] = NO_SHIP
                        }
                    } else if (shipId == -1) {
                        //はずす remove this ship
                        removeShip(index)
                    } else {
                        // 入队
                        for (y in index - 1 downTo 0) {
                            // 空位补齐
                            if (members[y] != NO_SHIP) {
                                index = y + 1
                                break
                            }
                        }
                        data["api_ship"][index] = shipId
                        // 替换
                        for (i in 0..members.size - 1) {
                            if (i != index && members[i] == shipId) {
                                if (i != index && members[i] == shipId) {
                                    if (replacedID != -1) {
                                        data["api_ship"][i] = replacedID
                                    } else {
                                        removeShip(i)
                                    }
                                    break
                                }
                            }
                        }
                    }
                    SetConditionTimer()
//                    if (index!= -1 && CanAnchorageRepairing)
                } else {
                    if (index != -1 && shipId != -1) {
                        // 替换
                        for (i in 0..members.size - 1) {
                            if (members[i] == shipId) {
                                if (replacedID != -1) {
                                    data["api_ship"][i] = replacedID
                                } else {
                                    removeShip(i)
                                }
//                                if (CanAnchorageRepairing)
                                break
                            }
                        }
                    }
                }
            }
            api_req_member.updatedeckname.name -> {
                val name = requestData["api_name"]
                if (name != null) data["api_name"] = name
            }
            else -> throw CatException()
        }
    }

    /**
     * @param index 0/1/2/3/4/5
     */
    fun removeShip(index: Int) {
        val size = members.size
        for (i in index + 1..size - 1) {
            data["api_ship"][i - 1] = data["api_ship"][i]
        }
        data["api_ship"][size - 1] = NO_SHIP
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