package kandroid.data.battle

import com.google.gson.JsonElement
import kandroid.data.GetAdmiralRank
import kandroid.data.RequestDataListener
import kandroid.data.ResponseDataListener
import kandroid.data.battle.BattleModes.*
import kandroid.observer.kcsapi.api_get_member
import kandroid.observer.kcsapi.api_port
import kandroid.observer.kcsapi.api_req_map
import kandroid.observer.kcsapi.api_req_member
import kandroid.utils.CatException
import kandroid.utils.SparseIntArray
import kandroid.utils.json.get
import kandroid.utils.json.int
import kandroid.utils.json.string

class BattleManager : ResponseDataListener, RequestDataListener {

    val compass = CompassData()

    var battleData: BattleData? = null

    var result: BattleResultData? = null

    var battleMode: BattleModes = Undefined

    var droppedShipCount: Int = 0

    var droppedEquipmentCount: Int = 0

    var droppedItemCount: SparseIntArray = SparseIntArray()

    var enemyAdmiralName: String? = null

    var enemyAdmiralRank: String? = null

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        when (apiName) {
            api_req_map.start.name,
            api_req_map.next.name -> {
                battleData = null
                result = null
                battleMode = Undefined
                compass.loadFromResponse(apiName, responseData)
            }
        // 通常昼战
            "api_req_sortie/battle" -> {
                battleMode = Day
                newDayBattle(apiName, responseData)
            }
        // 通常夜战
            "api_req_battle_midnight/battle" -> {
                battleMode = battleMode and Night
                newNightBattle(apiName, responseData)
            }
        // 开幕夜战
            "api_req_battle_midnight/sp_midnight" -> {
                battleMode = NightOnly
                newNightOnlyBattle(apiName, responseData)
            }
        // 夜转昼
            "api_req_sortie/night_to_day" -> {
                // 没玩过...好想玩一次(逃
                battleMode = NightToDay
                battleData = null
            }
        // 航空战
            "api_req_sortie/airbattle" -> {
                battleMode = AirBattle
                newDayBattle(apiName, responseData)
            }
        // 长距离航空战
            "api_req_sortie/ld_airbattle" -> {
                battleMode = LongAirRaid
                newDayBattle(apiName, responseData)
            }
        // 12V6机动昼战
            "api_req_combined_battle/battle" -> {
                battleMode = CombinedTaskForce and Day
                newDayBattle(apiName, responseData)
            }
        // 12V6水打昼战
            "api_req_combined_battle/battle_water" -> {
                battleMode = CombinedSurface and Day
                newDayBattle(apiName, responseData)
            }
        // 12V6夜战
            "api_req_combined_battle/midnight_battle" -> {
                battleMode = battleMode and Night
                newNightBattle(apiName, responseData)
            }
        // 12V6开幕夜战
            "api_req_combined_battle/sp_midnight" -> {
                battleMode = CombinedUndefined and NightOnly
                newNightOnlyBattle(apiName, responseData)
            }
        // 12V6航空战
            "api_req_combined_battle/airbattle" -> {
                battleMode = CombinedUndefined and AirBattle
                newDayBattle(apiName, responseData)
            }
        // 12V6长距离空袭战
            "api_req_combined_battle/ld_airbattle" -> {
                battleMode = CombinedUndefined and LongAirRaid
                newDayBattle(apiName, responseData)
            }
        // 6V12昼战
            "api_req_combined_battle/ec_battle" -> {
                battleMode = EnemyCombinedFleet and Day
                newDayBattle(apiName, responseData)
            }
        // **V12夜战
            "api_req_combined_battle/ec_midnight_battle" -> {
                battleMode = battleMode and Night
                newNightBattle(apiName, responseData)
            }
        // 12V12机动昼战
            "api_req_combined_battle/each_battle" -> {
                battleMode = CombinedTaskForce and EnemyCombinedFleet and Day
                newDayBattle(apiName, responseData)
            }
        // 12V12水打昼战
            "api_req_combined_battle/each_battle_water" -> {
                battleMode = CombinedSurface and EnemyCombinedFleet and Day
                newDayBattle(apiName, responseData)
            }
        // 演戏详细列表
            api_req_member.get_practice_enemyinfo.name -> {
                enemyAdmiralName = responseData["api_nickname"].string()
                enemyAdmiralRank = GetAdmiralRank(responseData["api_rank"].int())
            }
        // 演戏昼战
            "api_req_practice/battle" -> {
                battleMode = Practice
                newDayBattle(apiName, responseData)
            }
        // 演戏夜战
            "api_req_practice/midnight_battle" -> {
                battleMode = battleMode and Night
                newNightBattle(apiName, responseData)
            }
        // 战斗结果
            "api_req_sortie/battleresult",
            "api_req_combined_battle/battleresult",
            "api_req_practice/battle_result" -> {
                val result = BattleResultData()
                result.loadFromResponse(apiName, responseData)
                this.result = result
            }
            api_port.port.name -> {
                battleData = null
                result = null
                battleMode = Undefined
                droppedShipCount = 0
                droppedEquipmentCount = 0
                droppedItemCount.clear()
            }
            api_get_member.slot_item.name -> {
                droppedEquipmentCount = 0
            }
            else -> throw CatException()
        }
    }

    override fun loadFromRequest(apiName: String, requestData: MutableMap<String, String>) {
        when (apiName) {

        }
    }

    private fun newDayBattle(apiName: String, responseData: JsonElement) {
        val battleDay = BattleDay(battleMode)
        battleDay.loadFromResponse(apiName, responseData)
        battleData = battleDay
    }

    private fun newNightBattle(apiName: String, responseData: JsonElement) {
        val battleNight = BattleNight(battleData as? BattleDay, battleMode)
        battleNight.loadFromResponse(apiName, responseData)
        battleData = battleNight
    }

    private fun newNightOnlyBattle(apiName: String, responseData: JsonElement) {
        val battleNight = BattleNight(null, battleMode)
        battleNight.loadFromResponse(apiName, responseData)
        battleData = battleNight
    }

}