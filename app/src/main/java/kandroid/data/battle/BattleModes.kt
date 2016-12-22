package kandroid.data.battle

sealed class BattleModes(val flag: Int) {
    private interface Battle
    private interface Fleet

    //region @formatter:off
    object Undefined :         BattleModes(1),       Battle //未定義
    object Normal :            BattleModes(2),       Battle //昼夜戦(通常戦闘)
    object NightOnly :         BattleModes(4),       Battle //夜戦
    object NightDay :          BattleModes(8),       Battle //夜昼戦
    object AirBattle :         BattleModes(16),      Battle //航空戦
    object AirRaid :           BattleModes(32),      Battle //長距離空襲戦
    object Practice :          BattleModes(64),      Battle //演習

    object CombinedUndefined : BattleModes(0x10000), Fleet  //連合艦隊
    object CombinedTaskForce : BattleModes(0x20000), Fleet  //機動部隊
    object CombinedSurface :   BattleModes(0x40000), Fleet  //水上部隊

    class Mixed(flag: Int) :   BattleModes(flag)

    companion object {
        const val BattlePhaseMask = 0x0000FFFF
        const val CombinedMask =    0x7FFF0000
    }
    //endregion @formatter:on

    infix fun and(other: BattleModes): BattleModes {
        return if (when (this) {
            is Battle -> other is Fleet
            is Fleet -> other is Battle
            else -> false
        }) {
            Mixed(this.flag or other.flag)
        } else throw IllegalArgumentException()
    }

    override operator fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (this is Mixed && other is BattleModes) {
            return when (other) {
                is Battle -> {
                    flag and BattlePhaseMask == other.flag
                }
                is Fleet -> {
                    flag and CombinedMask == other.flag
                }
                is Mixed -> {
                    flag == other.flag
                }
                else -> false
            }
        } else return false
    }

    override fun hashCode(): Int = flag

    fun isCombinedBattle(): Boolean = this.flag and CombinedMask != 0
}