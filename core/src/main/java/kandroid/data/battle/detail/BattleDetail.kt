package kandroid.data.battle.detail

import kandroid.data.*
import kandroid.data.MasterEquipmentData
import kandroid.data.MasterShipData
import kandroid.data.battle.BattleData
import kandroid.data.battle.BattleDay
import kandroid.data.battle.BattleNight
import kandroid.utils.collection.Identifiable
import java.util.*

open class BattleDetail(val battleData: BattleData) {

    val fShip: MutableList<ShipData> = ArrayList()
    val fNowHp: MutableList<Int> = ArrayList()
    val fMaxHp: MutableList<Int> = ArrayList()
    val fParam: MutableList<List<Int>> = ArrayList()
    val eShip: MutableList<SimpleShipData> = ArrayList()

    var fSearching: Searching = Searching.Undefine
    var eSearching: Searching = Searching.Undefine
    var fFormation: Formation = Formation.Undefine
    var eFormation: Formation = Formation.Undefine
    var engagement: Engagement = Engagement.Undefine



    fun doParameters() {
        val parameters = battleData.parameters
        if (parameters != null) {
            parameters.friendFleets.forEach { it.membersInstance.forEach { fShip.add(it) } }
            val friendNowHps = parameters.friendNowHps
            val friendMaxHps = parameters.friendMaxHps
            val friendParam = parameters.friendParam
            for (i in 1..6) {
                fNowHp.add(friendNowHps[i])
                fMaxHp.add(friendMaxHps[i])
                fParam.add(friendParam[i])
            }

            if (battleData.battleMode.isCombined) {
                val friend2NowHps = parameters.friend2NowHps!!
                val friend2MaxHps = parameters.friend2MaxHps!!
                val friend2Param = parameters.friend2Param!!
                for (i in 1..6) {
                    fNowHp.add(friend2NowHps[i])
                    fMaxHp.add(friend2MaxHps[i])
                    fParam.add(friend2Param[i])
                }
            }

            val enemyId = parameters.enemy
            val enemyLv = parameters.enemyLv
            val enemyNowHps = parameters.enemyNowHps
            val enemyMaxHps = parameters.enemyMaxHps
            val enemyParam = parameters.enemyParam
            val enemySlot = parameters.enemySlot
            (1..6).mapTo(eShip) {
                SimpleShipData(enemyId[it], enemyLv[it],
                        enemyNowHps[it], enemyMaxHps[it],
                        enemyParam[it], enemySlot[it])
            }

            if (battleData.battleMode.isEnemyCombined) {
                val enemy2Id = parameters.enemy2!!
                val enemy2Lv = parameters.enemy2Lv!!
                val enemy2NowHps = parameters.enemy2NowHps!!
                val enemy2MaxHps = parameters.enemy2MaxHps!!
                val enemy2Param = parameters.enemy2Param!!
                val enemy2Slot = parameters.enemy2Slot!!
                (1..6).mapTo(eShip) {
                    SimpleShipData(enemy2Id[it], enemy2Lv[it],
                            enemy2NowHps[it], enemy2MaxHps[it],
                            enemy2Param[it], enemy2Slot[it])
                }
            }
        }
    }

    fun doSearching() {
        val searching = battleData.searching
        if (searching != null) {
            fSearching = Searching.fromApiInt(searching.searchingFriend)
            eSearching = Searching.fromApiInt(searching.searchingEnemy)
            fFormation = Formation.fromApiInt(searching.formationFriend)
            eFormation = Formation.fromApiInt(searching.formationEnemy)
            engagement = Engagement.fromApiInt(searching.engagementForm)
        }
    }

    class SimpleShipData(override val id: Int,
                         val lv: Int,
                         val nowHp: Int,
                         val maxHp: Int,
                         val parameter: List<Int>,
                         val equipmentsId: List<Int>) : Identifiable {
        val master: MasterShipData by lazy { KCDatabase.Master.ship[id] }
        val equipments: List<MasterEquipmentData> by lazy { equipmentsId.map { KCDatabase.Master.equipment[it] } }

        val firepower: Int by lazy { parameter[0] + getEquipmentParameter { firepower } }
        val torpedo: Int by lazy { parameter[1] + getEquipmentParameter { torpedo } }
        val aa: Int by lazy { parameter[2] + getEquipmentParameter { aa } }
        val armor: Int by lazy { parameter[3] + getEquipmentParameter { armor } }

        private inline fun getEquipmentParameter(p: MasterEquipmentData.() -> Int): Int {
            return equipmentsId.map { KCDatabase.Master.equipment[it] }.map { it.p() }.sum()
        }
    }



    companion object {
        fun getDetail(battleData: BattleData) {

        }
    }
}

class DayBattleDetail(val battleDay: BattleDay) : BattleDetail(battleDay) {

    fun doJetBaseAirAttack() {
        val airBattle = battleDay.jetBaseAirAttack
        if (airBattle != null) {
            for (airStrike in airBattle.airStrikes) {

            }
        }
    }

    data class ShellingAttack(val attacker: Int,
                              val attackType: Int,
                              val defender: List<Int>,
                              val damage: List<Int>)
}

class NightBattleDetail(val battleNight: BattleNight) : BattleDetail(battleNight) {

}