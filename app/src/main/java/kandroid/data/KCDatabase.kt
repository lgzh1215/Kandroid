package kandroid.data

import kandroid.data.battle.BattleManager
import kandroid.data.quest.QuestManager
import kandroid.utils.IDDictionary

object KCDatabase {
    val master = Start2Data()
    val equipments = IDDictionary<EquipmentData>()
    val arsenals = IDDictionary<ArsenalData>()
    val useItems = IDDictionary<UseItem>()
    val material = MaterialData()
    val admiral = AdmiralData()
    val ships = IDDictionary<ShipData>()
    /**
     * 入渠数据：id=1、2、3、4
     */
    val docks = IDDictionary<DockData>()
    val fleets = FleetManager()
    val mapInfo = IDDictionary<MapInfoData>()
    val quests = QuestManager()
    val battle = BattleManager()
    val baseAirCorps = IDDictionary<BaseAirCorpsData>()
}