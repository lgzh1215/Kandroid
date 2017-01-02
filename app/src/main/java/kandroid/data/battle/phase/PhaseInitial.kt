package kandroid.data.battle.phase

import kandroid.data.FleetData
import kandroid.data.KCDatabase
import kandroid.data.Start2Data
import kandroid.data.battle.BattleBase
import kandroid.utils.json.*
import java.util.*

@Suppress("JoinDeclarationAndAssignment")
class PhaseInitial(battle: BattleBase) : BasePhase(battle) {

    /**
     * 自军舰队
     */
    val friendFleets = ArrayList<FleetData>()
    /**
     * 敌舰队
     */
    val enemies = ArrayList<Start2Data.MasterShipData>()
    /**
     * 敌舰队lv
     */
    val enemiesLv = ArrayList<Int>()
    /**
     * 自军HP
     */
    val friendNowHps: List<Int>
    /**
     * 敌军HP
     */
    val enemyNowHps: List<Int>
    /**
     * 自军最大HP
     */
    val friendMaxHps: List<Int>
    /**
     * 自军属性
     */
    val friendParam: List<List<Int>>
    /**
     * 敌军最大HP
     */
    val enemyMaxHps: List<Int>
    /**
     * 敌军装备
     */
    val enemySlot: List<List<Int>>
    /**
     * 敌军属性(无强化?)
     */
    val enemyParam: List<List<Int>>

    init {
        val data = data.obj!!

        val deckId: Int
        if (data.has("api_dock_id"))
            deckId = data["api_dock_id"].int()
        else {
            deckId = data["api_deck_id"].int()
        }
        friendFleets.add(KCDatabase.fleets[deckId])

        data["api_ship_ke"].list<Int>().filter { it > -1 }
                .mapTo(enemies) { KCDatabase.master.masterShipData[it] }

        data["api_ship_lv"].list<Int>().filter { it > -1 }.mapTo(enemiesLv) { it }

        val nowHps = data["api_nowhps"].list<Int>()
        friendNowHps = nowHps.subList(1, 7)
        enemyNowHps = nowHps.subList(7, 13)

        val maxHps = data["api_maxhps"].list<Int>()
        friendMaxHps = maxHps.subList(1, 7)
        enemyMaxHps = maxHps.subList(7, 13)

        friendParam = data["api_fParam"].array!!.map { it.list<Int>() }

        enemyParam = data["api_eParam"].array!!.map { it.list<Int>() }

        enemySlot = data["api_eSlot"].array!!.map { it.list<Int>().filter { it > -1 } }

        if (isCombined) {
            // TODO
        }

        if (isEnemyCombined) {
            // TODO
        }
//        if (data.has("api_fParam_combined")) {
//            friendFleets.add(KCDatabase.fleets[2])
//        }

//        val nowHpsCombined = data["api_nowhps_combined"].array!!
//        val maxHpsCombined = data["api_maxhps_combined"].array!!
//
//        val isFriendCombined = data.has("api_fParam_combined")
//        val isEnemyCombined = data.has("api_eParam_combined")
    }
}