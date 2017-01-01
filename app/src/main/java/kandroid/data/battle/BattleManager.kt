package kandroid.data.battle

import com.google.gson.JsonElement
import kandroid.data.GetAdmiralRank
import kandroid.data.RequestDataListener
import kandroid.data.ResponseDataListener
import kandroid.data.battle.BattleModes.Undefined
import kandroid.observer.kcsapi.api_get_member
import kandroid.observer.kcsapi.api_req_map
import kandroid.observer.kcsapi.api_req_member
import kandroid.utils.CatException
import kandroid.utils.SparseIntArray
import kandroid.utils.json.get
import kandroid.utils.json.int
import kandroid.utils.json.string

class BattleManager : ResponseDataListener, RequestDataListener {

    var Compass: CompassData? = null

    var BattleDay: BattleDay? = null

    var BattleNight: BattleNight? = null

    var Result: BattleResultData? = null

    var BattleMode: BattleModes = Undefined

    var DroppedShipCount: Int = 0

    var DroppedEquipmentCount: Int = 0

    var DroppedItemCount: SparseIntArray = SparseIntArray()

    var EnemyAdmiralName: String? = null

    var EnemyAdmiralRank: String? = null

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        when (apiName) {
            api_req_map.start.name,
            "api_req_map/next" -> {
                BattleDay = null
                BattleNight = null
                Result = null
                BattleMode = Undefined
                val _compass = CompassData()
                _compass.loadFromResponse(apiName, responseData)
                Compass = _compass
            }
            "api_req_sortie/battle" -> {
                BattleMode = BattleModes.Normal
                val _battleDay = BattleNormalDay()
                _battleDay.loadFromResponse(apiName, responseData)
                BattleDay = _battleDay
            }
//            "api_req_battle_midnight/battle" -> {
//                BattleNight = BattleNormalNight()
//                BattleNight.TakeOverParameters(BattleDay)
//                BattleNight.loadFromResponse(apiName, responseData)
//            }
//            "api_req_battle_midnight/sp_midnight" -> {
//                BattleMode = BattleModes.NightOnly
//                BattleNight = BattleNightOnly()
//                BattleNight.loadFromResponse(apiName, responseData)
//            }
//            "api_req_sortie/airbattle" -> {
//                BattleMode = BattleModes.AirBattle
//                BattleDay = BattleAirBattle()
//                BattleDay.loadFromResponse(apiName, responseData)
//            }
//            "api_req_sortie/ld_airbattle" -> {
//                BattleMode = BattleModes.AirRaid
//                BattleDay = BattleAirRaid()
//                BattleDay.loadFromResponse(apiName, responseData)
//            }
//            "api_req_combined_battle/battle" -> {
//                BattleMode = BattleModes.Normal and BattleModes.CombinedTaskForce
//                BattleDay = BattleCombinedNormalDay()
//                BattleDay.loadFromResponse(apiName, responseData)
//            }
//
//            "api_req_combined_battle/midnight_battle" -> {
//                BattleNight = BattleCombinedNormalNight()
//                BattleNight.loadFromResponse(apiName, responseData)
//            }
//
//            "api_req_combined_battle/sp_midnight" -> {
//                BattleMode = BattleModes.NightOnly and BattleModes.CombinedUndefined
//                BattleNight = BattleCombinedNightOnly()
//                BattleNight.loadFromResponse(apiName, responseData)
//            }
//
//            "api_req_combined_battle/airbattle" -> {
//                BattleMode = BattleModes.AirBattle and BattleModes.CombinedTaskForce
//                BattleDay = BattleCombinedAirBattle()
//                BattleDay.loadFromResponse(apiName, responseData)
//            }
//
//            "api_req_combined_battle/battle_water" -> {
//                BattleMode = BattleModes.Normal and BattleModes.CombinedSurface
//                BattleDay = BattleCombinedWater()
//                BattleDay.loadFromResponse(apiName, responseData)
//            }
//
//            "api_req_combined_battle/ld_airbattle" -> {
//                BattleMode = BattleModes.AirRaid and BattleModes.CombinedTaskForce
//                BattleDay = BattleCombinedAirRaid()
//                BattleDay.loadFromResponse(apiName, responseData)
//            }
//
            api_req_member.get_practice_enemyinfo.name -> {
                EnemyAdmiralName = responseData["api_nickname"].string()
                EnemyAdmiralRank = GetAdmiralRank(responseData["api_rank"].int())
            }
//
//            "api_req_practice/battle" -> {
//                BattleMode = BattleModes.Practice
//                BattleDay = BattlePracticeDay()
//                BattleDay.loadFromResponse(apiName, responseData)
//            }
//
//            "api_req_practice/midnight_battle" -> {
//                BattleNight = BattlePracticeNight()
//                BattleNight.TakeOverParameters(BattleDay)
//                BattleNight.loadFromResponse(apiName, responseData)
//            }
//
//            "api_req_sortie/battleresult",
//            "api_req_combined_battle/battleresult",
//            "api_req_practice/battle_result" -> {
//                Result = BattleResultData()
//                Result.loadFromResponse(apiName, responseData)
//                BattleFinished()
//            }
            "api_port/port" -> {
                Compass = null
                BattleDay = null
                BattleNight = null
                Result = null
                BattleMode = Undefined
                DroppedShipCount = 0
                DroppedEquipmentCount = 0
                DroppedItemCount.clear()
            }
            "api_get_member/slot_item" -> {
                DroppedEquipmentCount = 0
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