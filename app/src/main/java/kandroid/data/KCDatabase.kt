package kandroid.data

import kandroid.data.*
import kandroid.data.battle.BattleManager
import kandroid.utils.IDDictionary

object KCDatabase {
    val master: Start2Data = Start2Data()

    val equipments: IDDictionary<EquipmentData> = IDDictionary()
    val arsenals: IDDictionary<ArsenalData> = IDDictionary()
    val useItems: IDDictionary<UseItem> = IDDictionary()

    val material: MaterialData = MaterialData()
    val admiral: AdmiralData = AdmiralData()
    val ships: IDDictionary<ShipData> = IDDictionary()

    /**
     * 入渠数据：id=1、2、3、4
     */
    val docks: IDDictionary<DockData> = IDDictionary()
    val fleets: FleetManager = FleetManager()

    val mapInfo: IDDictionary<MapInfoData> = IDDictionary()

    val battle: BattleManager = BattleManager()
}