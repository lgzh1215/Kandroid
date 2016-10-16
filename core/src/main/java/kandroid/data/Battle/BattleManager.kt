package kandroid.data.battle

import com.google.gson.JsonElement
import kandroid.data.RequestDataListener
import kandroid.data.ResponseDataListener
import kandroid.observer.kcsapi.api_get_member
import kandroid.observer.kcsapi.api_req_map
import kandroid.utils.CatException
import kandroid.utils.SparseIntArray

class BattleManager : ResponseDataListener, RequestDataListener {

    var Compass: CompassData? = null

    var BattleDay: BattleDay? = null

    var BattleNight: BattleNight? = null

    var Result: BattleResultData? = null

    var BattleMode: BattleModes = BattleModes.Undefined

    var DroppedShipCount: Int = 0

    var DroppedEquipmentCount: Int = 0

    var DroppedItemCount: SparseIntArray = SparseIntArray()

    var EnemyAdmiralName: String? = null

    var EnemyAdmiralRank: String? = null

    enum class BattleModes constructor(val flags: Int) {
        Undefined(1), //未定義
        Normal(2), //昼夜戦(通常戦闘)
        NightOnly(4), //夜戦
        NightDay(8), //夜昼戦
        AirBattle(16), //航空戦
        AirRaid(32), //長距離空襲戦
        Practice(64), //演習
        BattlePhaseMask(0xFFFF), //戦闘形態マスク
        CombinedTaskForce(0x10000), //機動部隊
        CombinedSurface(0x20000), //水上部隊
        CombinedMask(0x7FFF0000) //連合艦隊仕様
    }

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        when (apiName) {
            api_req_map.start.name,
            "api_req_map/next" -> {
                BattleDay = null
                BattleNight = null
                Result = null
                BattleMode = BattleModes.Undefined
                val _Compass = CompassData()
                _Compass.loadFromResponse(apiName, responseData)
                Compass = _Compass
            }
            "api_port/port" -> {
                Compass = null
                BattleDay = null
                BattleNight = null
                Result = null
                BattleMode = BattleModes.Undefined
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

    override fun loadFromRequest(apiName: String, requestData: Map<String, String>) {
        when (apiName) {

        }
    }

}