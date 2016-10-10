package kandroid.data.Battle

import kandroid.data.ResponseDataListner
import kandroid.observer.RawData
import kandroid.utils.SparseIntArray

class BattleManager : ResponseDataListner {

    var Compass: CompassData = CompassData()

    var BattleDay: BattleDay? = null

    var BattleNight: BattleNight? = null

    var Result: BattleResultData? = null

    var BattleMode: BattleModes = BattleModes.Undefined

    var DroppedShipCount: Int = 0

    var DroppedEquipmentCount: Int = 0

    var DroppedItemCount: SparseIntArray

    var EnemyAdmiralName: String? = null

    var EnemyAdmiralRank: String? = null

    init {
        DroppedItemCount = SparseIntArray()
    }

    enum class BattleModes constructor(val flags: Int) {
        Undefined(1), //未定義
        Normal(2), //昼夜戦(通常戦闘)
        Night(4), //夜戦
        NightDay(8), //夜昼戦
        AirBattle(16), //航空戦
        AirRaid(32), //長距離空襲戦
        Practice(64), //演習
        BattlePhaseMask(0xFFFF), //戦闘形態マスク
        CombinedTaskForce(0x10000), //機動部隊
        CombinedSurface(0x20000), //水上部隊
        CombinedMask(0x7FFF0000) //連合艦隊仕様
    }

    override fun loadFromResponse(apiName: String, rawData: RawData) {
        when (apiName) {
            "api_req_map/start", "api_req_map/next" -> {
                BattleDay = null
                BattleNight = null
                Result = null
                BattleMode = BattleModes.Undefined
                Compass = CompassData()
                Compass.loadFromResponse(apiName, rawData)
            }
        }
    }
}