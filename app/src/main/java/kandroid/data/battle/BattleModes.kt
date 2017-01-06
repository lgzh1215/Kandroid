package kandroid.data.battle

sealed class BattleModes(val flag: Int) {
    //region @formatter:off
    object Undefined :          BattleModes(0b0)            // 未定義
    object Day :                BattleModes(0b1)            // 昼战
    object Night :              BattleModes(0b10)           // 夜戦
    object NightOnly :          BattleModes(0b100)          // 开幕夜戦
    object NightToDay :         BattleModes(0b1000)         // 夜转昼
    object AirBattle :          BattleModes(0b10000)        // 航空戦
    object LongAirRaid :        BattleModes(0b100000)       // 長距離空襲戦

    object Practice :           BattleModes(0b1000000)      // 演習

    object CombinedUndefined :  BattleModes(0b10000000)     // 連合艦隊
    object CombinedTaskForce :  BattleModes(0b100000000)    // 機動部隊
    object CombinedSurface :    BattleModes(0b1000000000)   // 水上部隊

    object EnemyCombinedFleet : BattleModes(0b10000000000)  // 敵連合艦隊
    //endregion @formatter:on

    private class Mixed(flag: Int) : BattleModes(flag)

    infix fun and(other: BattleModes): BattleModes {
        return Mixed(this.flag or other.flag)
    }

    val isDay: Boolean
        get() = flag and Day.flag != 0
    val isNight: Boolean
        get() = flag and (Night and NightOnly).flag != 0
    val isCombined: Boolean
        get() = flag and (CombinedUndefined and CombinedTaskForce and CombinedSurface).flag != 0
    val isEnemyCombined: Boolean
        get() = flag and EnemyCombinedFleet.flag != 0
    val isPractice: Boolean
        get() = flag and Practice.flag != 0
}