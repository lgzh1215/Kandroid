package kandroid.data.battle.phase

import kandroid.data.FleetData
import kandroid.data.KCDatabase
import kandroid.data.Start2Data
import kandroid.data.battle.BattleBase
import kandroid.utils.json.array
import kandroid.utils.json.int
import kandroid.utils.json.list
import kandroid.utils.json.obj
import java.util.*

//@Suppress("JoinDeclarationAndAssignment")
class PhaseInitial(battle: BattleBase) : BasePhase(battle) {

    /**
     * 自军舰队
     */
    val friendFleets = ArrayList<FleetData>()

    /**
     * 自军HP
     */
    val friendNowHps: List<Int>
    /**
     * 自军最大HP
     */
    val friendMaxHps: List<Int>
    /**
     * 自军属性
     */
    val friendParam: List<List<Int>>

    /**
     * 自军二队HP
     */
    var friend2NowHps: List<Int>? = null
    /**
     * 自军二队最大HP
     */
    var friend2MaxHps: List<Int>? = null
    /**
     * 自军二队属性
     */
    var friend2Param: List<List<Int>>? = null

    /**
     * 敌舰队
     */
    val enemy: List<Start2Data.MasterShipData>
    /**
     * 敌舰队Lv
     */
    val enemyLv: List<Int>
    /**
     * 敌军HP
     */
    val enemyNowHps: List<Int>
    /**
     * 敌军最大HP
     */
    val enemyMaxHps: List<Int>
    /**
     * 敌军装备
     */
    val enemySlot: List<List<Int>>
    /**
     * 敌军属性(裸装)
     */
    val enemyParam: List<List<Int>>

    /**
     * 敌二队
     */
    var enemy2: List<Start2Data.MasterShipData>? = null
        private set
    /**
     * 敌二队Lv
     */
    var enemy2Lvs: List<Int>? = null
        private set
    /**
     * 敌二队Hp
     */
    var enemy2NowHps: List<Int>? = null
        private set
    /**
     * 敌二队最大HP
     */
    var enemy2MaxHps: List<Int>? = null
        private set
    /**
     * 敌二队装备
     */
    var enemy2Slot: List<List<Int>>? = null
        private set
    /**
     * 敌二队属性(裸装)
     */
    var enemy2Param: List<List<Int>>? = null
        private set

    init {
        // TODO 懒加载
        val data = data.obj!!

        val deckId: Int
        if (data.has("api_dock_id")) {
            deckId = data["api_dock_id"].int()
        } else {
            deckId = data["api_deck_id"].int()
        }
        friendFleets.add(KCDatabase.fleets[deckId])

        enemy = data["api_ship_ke"].list<Int>().filter { it > -1 }
                .map { KCDatabase.master.masterShipData[it] }

        enemyLv = data["api_ship_lv"].list<Int>().filter { it > -1 }

        val nowHps = data["api_nowhps"].list<Int>()
        friendNowHps = nowHps.subList(1, 7)
        enemyNowHps = nowHps.subList(7, 13)

        val maxHps = data["api_maxhps"].list<Int>()
        friendMaxHps = maxHps.subList(1, 7)
        enemyMaxHps = maxHps.subList(7, 13)

        friendParam = data["api_fParam"].array!!.map { it.list<Int>() }

        enemyParam = data["api_eParam"].array!!.map { it.list<Int>() }

        enemySlot = data["api_eSlot"].array!!.map { it.list<Int>().filter { it > -1 } }


        var nowHpsCombined: List<Int>? = null
        var maxHpsCombined: List<Int>? = null

        if (isCombined) {
            nowHpsCombined = data["api_nowhps_combined"].list<Int>()
            maxHpsCombined = data["api_maxhps_combined"].list<Int>()

            friendFleets.add(KCDatabase.fleets[2])

            friend2NowHps = nowHpsCombined.subList(1, 7)
            friend2MaxHps = maxHpsCombined.subList(1, 7)

            friend2Param = data["api_fParam_combined"].array!!.map { it.list<Int>() }
        }

        if (isEnemyCombined) {
            enemy2 = data["api_ship_ke_combined"].list<Int>().filter { it > -1 }
                    .map { KCDatabase.master.masterShipData[it] }
            enemy2Lvs = data["api_ship_lv"].list<Int>().filter { it > -1 }

            if (nowHpsCombined != null)
                enemy2NowHps = nowHpsCombined.subList(7, 13)

            if (maxHpsCombined != null)
                enemy2MaxHps = maxHpsCombined.subList(7, 13)

            enemy2Slot = data["api_eSlot_combined"].array!!.map { it.list<Int>().filter { it > -1 } }

            enemy2Param = data["api_eParam_combined"].array!!.map { it.list<Int>() }
        }
    }
}