package kandroid.observer.kcsapi

import kandroid.data.Start2Data
import kandroid.newdata.KCDatabase
import kandroid.observer.NewApiBase
import kandroid.observer.RawData
import kandroid.utils.json.*

object api_start2 : NewApiBase() {
    override val apiName: String get() = "api_start2"

    override fun onDataReceived(rawData: RawData) {
        val data = JsonParser.parse(rawData.responseString)["api_data"].obj ?: return

        // 特別置換処理
        data["api_mst_stype"][7]["api_name"] = "巡洋戦艦"

        // api_mst_ship
        val api_mst_ship = data["api_mst_ship"].array
        if (api_mst_ship != null) {
            for (elem in api_mst_ship) {
                val id = elem["api_id"].int()
                var ship = KCDatabase.master.masterShipData[id]
                if (ship == null) {
                    ship = Start2Data.MasterShipData()
                    ship.loadFromResponse(apiName, elem)
                    KCDatabase.master.masterShipData.put(ship)
                } else {
                    ship.loadFromResponse(apiName, elem)
                }

            }
        }

        // 改装関連のデータ設定
        for (ship in KCDatabase.master.masterShipData) {
            val remodelID = ship.remodelAfterShipId
            if (remodelID != 0) {
                KCDatabase.master.masterShipData[remodelID]?.remodelBeforeShipId = ship.shipId
            }
        }

        // api_mst_slotitem_equiptype
        val api_mst_slotitem_equiptype = data["api_mst_slotitem_equiptype"].array
        if (api_mst_slotitem_equiptype != null) {
            for (elem in api_mst_slotitem_equiptype) {
                val id = elem["api_id"].int()
                var equipmentType = KCDatabase.master.equipmentType[id]
                if (equipmentType == null) {
                    equipmentType = Start2Data.EquipmentType()
                    equipmentType.loadFromResponse(apiName, elem)
                    KCDatabase.master.equipmentType.put(equipmentType)
                } else {
                    equipmentType.loadFromResponse(apiName, elem)
                }
            }
        }

        // api_mst_stype
        val api_mst_stype = data["api_mst_stype"].array
        if (api_mst_stype != null) {
            for (elem in api_mst_stype) {
                val id = elem["api_id"].int()
                var shipType = KCDatabase.master.shipType[id]
                if (shipType == null) {
                    shipType = Start2Data.ShipType()
                    shipType.loadFromResponse(apiName, elem)
                    KCDatabase.master.shipType.put(shipType)
                } else {
                    shipType.loadFromResponse(apiName, elem)
                }
            }
        }

        // api_mst_slotitem
        val api_mst_slotitem = data["api_mst_slotitem"].array
        if (api_mst_slotitem != null) {
            for (elem in api_mst_slotitem) {
                val id = elem["api_id"].int()
                var equipment = KCDatabase.master.masterEquipmentData[id]
                if (equipment == null) {
                    equipment = Start2Data.MasterEquipmentData()
                    equipment.loadFromResponse(apiName, elem)
                    KCDatabase.master.masterEquipmentData.put(equipment)
                } else {
                    equipment.loadFromResponse(apiName, elem)
                }
            }
        }

        // api_mst_useitem
        val api_mst_useitem = data["api_mst_useitem"].array
        if (api_mst_useitem != null) {
            for (elem in api_mst_useitem) {
                val id = elem["api_id"].int()
                var useItem = KCDatabase.master.masterUseItemData[id]
                if (useItem == null) {
                    useItem = Start2Data.MasterUseItemData()
                    useItem.loadFromResponse(apiName, elem)
                    KCDatabase.master.masterUseItemData.put(useItem)
                } else {
                    useItem.loadFromResponse(apiName, elem)
                }
            }
        }

        // api_mst_mapinfo
        val api_mst_mapinfo = data["api_mst_mapinfo"].array
        if (api_mst_mapinfo != null) {
            for (elem in api_mst_mapinfo) {
                val id = elem["api_id"].int()
                var mapInfo = KCDatabase.master.masterMapInfoData[id]
                if (mapInfo == null) {
                    mapInfo = Start2Data.MasterMapInfoData()
                    mapInfo.loadFromResponse(apiName, elem)
                    KCDatabase.master.masterMapInfoData.put(mapInfo)
                } else {
                    mapInfo.loadFromResponse(apiName, elem)
                }
            }
        }

        // api_mst_mission
        val api_mst_mission = data["api_mst_mission"].array
        if (api_mst_mission != null) {
            for (elem in api_mst_mission) {
                val id = elem["api_id"].int()
                var mapInfo = KCDatabase.master.masterMissionData[id]
                if (mapInfo == null) {
                    mapInfo = Start2Data.MasterMissionData()
                    mapInfo.loadFromResponse(apiName, elem)
                    KCDatabase.master.masterMissionData.put(mapInfo)
                } else {
                    mapInfo.loadFromResponse(apiName, elem)
                }
            }
        }

        //TODO api_mst_shipupgrade
    }
}