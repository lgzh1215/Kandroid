package kandroid.observer.kcsapi

import kandroid.config.Config
import kandroid.data.KCDatabase
import kandroid.data.Start2Data
import kandroid.observer.ApiBase
import kandroid.observer.ByteArrayRawData
import kandroid.observer.RawData
import kandroid.utils.SparseIntArray
import kandroid.utils.json.*
import org.apache.commons.io.FileUtils
import java.nio.charset.Charset

object api_start2 : ApiBase() {
    override val name: String get() = "api_start2"

    override fun onDataReceived(rawData: RawData) {
        // 保存Start2以便下次载入
        val responseString = rawData.responseString
        if (rawData is ByteArrayRawData) {
            val file = Config.getSaveUserDataFile("api_start2")
            FileUtils.writeStringToFile(file, responseString, Charset.defaultCharset())
        }
        val data = JsonParser.parse(responseString)["api_data"].obj ?: return

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
                    ship.loadFromResponse(name, elem)
                    KCDatabase.master.masterShipData.put(ship)
                } else {
                    ship.loadFromResponse(name, elem)
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

        // TODO api_mst_shipgraph

        // api_mst_slotitem_equiptype
        val api_mst_slotitem_equiptype = data["api_mst_slotitem_equiptype"].array
        if (api_mst_slotitem_equiptype != null) {
            for (elem in api_mst_slotitem_equiptype) {
                val id = elem["api_id"].int()
                var equipmentType = KCDatabase.master.equipmentType[id]
                if (equipmentType == null) {
                    equipmentType = Start2Data.EquipmentType()
                    equipmentType.loadFromResponse(name, elem)
                    KCDatabase.master.equipmentType.put(equipmentType)
                } else {
                    equipmentType.loadFromResponse(name, elem)
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
                    shipType.loadFromResponse(name, elem)
                    KCDatabase.master.shipType.put(shipType)
                } else {
                    shipType.loadFromResponse(name, elem)
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
                    equipment.loadFromResponse(name, elem)
                    KCDatabase.master.masterEquipmentData.put(equipment)
                } else {
                    equipment.loadFromResponse(name, elem)
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
                    useItem.loadFromResponse(name, elem)
                    KCDatabase.master.masterUseItemData.put(useItem)
                } else {
                    useItem.loadFromResponse(name, elem)
                }
            }
        }

        // api_mst_maparea
        val api_mst_maparea = data["api_mst_maparea"].array
        if (api_mst_maparea != null) {
            for (elem in api_mst_maparea) {
                val id = elem["api_id"].int()
                var mapArea = KCDatabase.master.masterMapAreaData[id]
                if (mapArea == null) {
                    mapArea = Start2Data.MasterMapAreaData()
                    mapArea.loadFromResponse(name, elem)
                    KCDatabase.master.masterMapAreaData.put(mapArea)
                } else {
                    mapArea.loadFromResponse(name, elem)
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
                    mapInfo.loadFromResponse(name, elem)
                    KCDatabase.master.masterMapInfoData.put(mapInfo)
                } else {
                    mapInfo.loadFromResponse(name, elem)
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
                    mapInfo.loadFromResponse(name, elem)
                    KCDatabase.master.masterMissionData.put(mapInfo)
                } else {
                    mapInfo.loadFromResponse(name, elem)
                }
            }
        }

        // api_mst_shipupgrade
        val api_mst_shipupgrade = data["api_mst_shipupgrade"].array
        if (api_mst_shipupgrade != null) {
            val upgradeTimes = SparseIntArray(70) // 记录本船id和第几次改造
            for (elem in api_mst_shipupgrade) {
                val idAfter = elem["api_id"].int() // 本船的id
                val idBefore = elem["api_current_ship_id"].int() // 本船改造前的id
                val level = elem["api_upgrade_level"].int() // 这是本船第几次改造
                val shipAfter = KCDatabase.master.masterShipData[idAfter]

                // 例：太太改二咸(api_id=461)可以由太太改(id=288)和太太改二甲(id=466)改造而来
                val valueIfKeyNotFound = -1
                val l = upgradeTimes[idAfter, valueIfKeyNotFound]
                if (l != valueIfKeyNotFound) { // 即upgradeLevels.containsKey(idAfter)
                    if (level < l) { //
                        shipAfter?.remodelBeforeShipId = idBefore
                        upgradeTimes.put(idAfter, level)
                    }
                } else { //
                    shipAfter?.remodelBeforeShipId = idBefore
                    upgradeTimes.put(idAfter, level)
                }

                val shipBefore = KCDatabase.master.masterShipData[idBefore]
                if (shipBefore != null) {
                    shipBefore.needBlueprint = elem["api_drawing_count"].int()
                    shipBefore.needCatapult = elem["api_catapult_count"].int()
                }
            }
        }
    }
}