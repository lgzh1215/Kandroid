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

    var BattleDay: BattleDay? = null

    var BattleNight: BattleNight? = null

    var Result: BattleResultData? = null

    var battleMode: BattleModes = Undefined

    var DroppedShipCount: Int = 0

    var DroppedEquipmentCount: Int = 0

    var DroppedItemCount: SparseIntArray = SparseIntArray()

    var EnemyAdmiralName: String? = null

    var EnemyAdmiralRank: String? = null

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        when (apiName) {
            api_req_map.start.name,
            api_req_map.next.name -> {
                BattleDay = null
                BattleNight = null
                Result = null
                battleMode = Undefined
                compass.loadFromResponse(apiName, responseData)
                if (compass.isHasAirRaid) {
//                    BattleMode = BattleModes.BaseAirRaid
                    // TODO
//                    BattleDay = new BattleBaseAirRaid();
//                    BattleDay.LoadFromResponse( apiname, Compass.AirRaidData );
//                    WriteBattleLog();
                }
            }
        // 通常昼战
            "api_req_sortie/battle" -> {
                battleMode = Day
                val battleDay = BattleDay(battleMode)
                battleDay.loadFromResponse(apiName, responseData)
                BattleDay = battleDay
            }
        // 通常夜战
            "api_req_battle_midnight/battle" -> {
                battleMode = battleMode and Night
            }
        // 开幕夜战
            "api_req_battle_midnight/sp_midnight" -> {
                battleMode = NightOnly
            }
        // 夜转昼
            "api_req_sortie/night_to_day" -> {
                // 没玩过...好想玩一次(逃
                battleMode = NightToDay
            }
        // 航空战
            "api_req_sortie/airbattle" -> {
                battleMode = AirBattle
            }
        // 长距离航空战
            "api_req_sortie/ld_airbattle" -> {
                battleMode = LongAirRaid
            }
        // 12V6机动昼战
            "api_req_combined_battle/battle" -> {
                battleMode = CombinedTaskForce and Day
            }
        // 12V6水打昼战
            "api_req_combined_battle/battle_water" -> {
                battleMode = CombinedSurface and Day
            }
        // 12V6夜战
            "api_req_combined_battle/midnight_battle" -> {
                battleMode = battleMode and Night
            }
        // 12V6开幕夜战
            "api_req_combined_battle/sp_midnight" -> {
                battleMode = CombinedUndefined and NightOnly
            }
        // 12V6航空战
            "api_req_combined_battle/airbattle" -> {
                battleMode = CombinedUndefined and AirBattle
            }
        // 12V6长距离空袭战
            "api_req_combined_battle/ld_airbattle" -> {
                battleMode = CombinedUndefined and LongAirRaid
            }
        // 6V12昼战
            "api_req_combined_battle/ec_battle" -> {
                battleMode = EnemyCombinedFleet and Day
            }
        // **V12夜战
            "api_req_combined_battle/ec_midnight_battle" -> {
                battleMode = battleMode and Night
            }
        // 12V12机动昼战
            "api_req_combined_battle/each_battle" -> {
                battleMode = CombinedTaskForce and EnemyCombinedFleet and Day
            }
        // 12V12水打昼战
            "api_req_combined_battle/each_battle_water" -> {
                battleMode = CombinedSurface and EnemyCombinedFleet and Day
            }
        // 演戏详细列表
            api_req_member.get_practice_enemyinfo.name -> {
                EnemyAdmiralName = responseData["api_nickname"].string()
                EnemyAdmiralRank = GetAdmiralRank(responseData["api_rank"].int())
            }
        // 演戏昼战
            "api_req_practice/battle" -> {
            }
        // 演戏夜战
            "api_req_practice/midnight_battle" -> {
            }
        // 战斗结果
            "api_req_sortie/battleresult",
            "api_req_combined_battle/battleresult",
            "api_req_practice/battle_result" -> {
            }
            api_port.port.name -> {
                BattleDay = null
                BattleNight = null
                Result = null
                battleMode = Undefined
                DroppedShipCount = 0
                DroppedEquipmentCount = 0
                DroppedItemCount.clear()
            }
            api_get_member.slot_item.name -> {
                DroppedEquipmentCount = 0
            }
            else -> throw CatException()
        }
    }

    override fun loadFromRequest(apiName: String, requestData: MutableMap<String, String>) {
        when (apiName) {

        }
    }

}