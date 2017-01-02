package kandroid.data.battle

sealed class BattleModes(number: Int, flag: Int = 0) {
    //region @formatter:off
    object Undefined :          BattleModes(0)   // 未定義
    object Day :                BattleModes(1)   // 昼战
    object Night :              BattleModes(2)   // 夜戦
    object NightOnly :          BattleModes(3)   // 开幕夜戦
    object NightToDay :         BattleModes(4)   // 夜转昼
    object AirBattle :          BattleModes(5)   // 航空戦
    object LongAirRaid :        BattleModes(6)   // 長距離空襲戦

    object Practice :           BattleModes(7)   // 演習

    object CombinedUndefined :  BattleModes(8)   // 連合艦隊
    object CombinedTaskForce :  BattleModes(9)   // 機動部隊
    object CombinedSurface :    BattleModes(10)  // 水上部隊

    object EnemyCombinedFleet : BattleModes(11)  // 敵連合艦隊
    //endregion @formatter:on

    private class Mixed(flag: Int) : BattleModes(0, flag)

    val flag: Int = getFlag(number, flag)

    fun getFlag(number: Int, flag: Int): Int = if (flag != 0) flag else 1 shl number

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