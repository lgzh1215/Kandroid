package kandroid.data.battle.phase

import com.google.gson.JsonElement
import kandroid.data.AdmiralData
import kandroid.data.JsonWrapper
import kandroid.data.KCDatabase
import kandroid.data.ResponseDataListener
import kandroid.data.battle.BattleBase

abstract class BasePhase(val battle: BattleBase) {

    protected val data: JsonElement? = battle.data

    fun isIndexFriend(index: Int) = (0 <= index && index < 6) || (12 <= index && index < 18)
    fun isIndexEnemy(index: Int) = (6 <= index && index < 12) || (18 <= index && index < 24)

    val isPractice: Boolean = battle.battleMode.isPractice
    val isCombined: Boolean = battle.battleMode.isCombined
    val isEnemyCombined: Boolean = battle.battleMode.isEnemyCombined

    open val isAvailable: Boolean = data != null

    fun IntArray.skip(skipCount: Int = 1): IntArray {
        return this.copyOfRange(skipCount, this.size)
    }

    fun <T> List<T>.skip(skipCount: Int = 1): List<T> {
        return this.drop(skipCount)
    }

    /**
     * 被击
     * @param hps 各舰hp
     * @param index 挨打的那个家伙
     * @param damage 伤害
     */
    fun addDamage(hps: IntArray, index: Int, damage: Int) {
        hps[index] -= Math.max(damage, 0)

        if (hps[index] <= 0 && isIndexFriend(index) && !isPractice) {
            // 电子竞技没有视力
//            val fleetId = if (index < 6) battle.initial!!.friendFleetId else 2
//            val ship = KCDatabase.fleets[fleetId].membersInstance[index % 6] ?: return
//
//            // 开洞的损管优先
//            when (ship.expansionSlotMaster) {
//                42 -> {// 损管发动
//                    hps[index] = (ship.hpMax * 0.2).toInt()
//                    return
//                }
//                43 -> {// 女神发动
//                    hps[index] = ship.hpMax
//                    return
//                }
//            }
//            for (equipmentId in ship.slotMaster) {
//                when (equipmentId) {
//                    42 -> {// 损管发动
//                        hps[index] = (ship.hpMax * 0.2).toInt()
//                        return
//                    }
//                    43 -> {// 女神发动
//                        hps[index] = ship.hpMax
//                        return
//                    }
//                }
//            }
        }
    }
}