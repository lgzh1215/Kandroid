package kandroid.newdata

import kandroid.data.*
import kandroid.utils.IDDictionary

object KCDatabase {
    val master : Start2Data = Start2Data()

    val material: MaterialData = MaterialData()
    val admiral: AdmiralData = AdmiralData()
    val ships: IDDictionary<ShipData> = IDDictionary()
    val docks: IDDictionary<DockData> = IDDictionary()
    val fleets: FleetManager = FleetManager()
}