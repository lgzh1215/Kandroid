package kandroid.observer.kcsapi

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import kandroid.data.*
import kandroid.observer.ApiBase
import kandroid.observer.RawData
import kandroid.utils.exception.CatException
import kandroid.utils.json.*

object api_get_member {

    object require_info : ApiBase() {
        override val name: String get() = "api_get_member/require_info"

        override fun onDataReceived(rawData: RawData) {
            val data = rawData.api_data().obj ?: return

            // api_slot_item
            val api_slot_item = data["api_slot_item"].array
            if (api_slot_item != null) {
                KCDatabase.equipments.clear()
                for (elem in api_slot_item) {
                    val equipment = EquipmentData()
                    equipment.loadFromResponse(name, elem)
                    KCDatabase.equipments.put(equipment)
                }
            }

            // api_kdock
            val api_kdock = data["api_kdock"].array
            if (api_kdock != null) {
                for (elem in api_kdock) {
                    val id = elem["api_id"].int()
                    var kdock = KCDatabase.arsenals[id]
                    if (kdock == null) {
                        kdock = ArsenalData()
                        kdock.loadFromResponse(name, elem)
                        KCDatabase.arsenals.put(kdock)
                    } else {
                        kdock.loadFromResponse(name, elem)
                    }
                }
            }

            // api_useitem
            val api_useitem = data["api_useitem"].array
            if (api_useitem != null) {
                KCDatabase.useItems.clear()
                for (elem in api_useitem) {
                    val useItem = UseItem()
                    useItem.loadFromResponse(name, elem)
                    KCDatabase.useItems.put(useItem)
                }
            }
        }
    }

    object slot_item : ApiBase() {
        override val name: String get() = "api_get_member/slot_item"

        override fun onDataReceived(rawData: RawData) {
            val data = rawData.api_data().array ?: return

            KCDatabase.equipments.clear()
            for (elem in data) {
                val equipment = EquipmentData()
                equipment.loadFromResponse(name, elem)
                KCDatabase.equipments.put(equipment)
            }

            KCDatabase.battle.loadFromResponse(name, data)
        }
    }

    object kdock : ApiBase() {
        override val name: String get() = "api_get_member/kdock"

        override fun onDataReceived(rawData: RawData) {
            val data = rawData.api_data().array ?: return

            for (elem in data) {
                val id = elem["api_id"].int()
                var kdock = KCDatabase.arsenals[id]
                if (kdock == null) {
                    kdock = ArsenalData()
                    kdock.loadFromResponse(name, elem)
                    KCDatabase.arsenals.put(kdock)
                } else {
                    kdock.loadFromResponse(name, elem)
                }
            }
        }
    }

    object useitem : ApiBase() {
        override val name: String get() = "api_get_member/useitem"

        override fun onDataReceived(rawData: RawData) {
            val data = rawData.api_data().array ?: return

            KCDatabase.useItems.clear()
            for (elem in data) {
                val useItem = UseItem()
                useItem.loadFromResponse(name, elem)
                KCDatabase.useItems.put(useItem)
            }
        }
    }

    object material : ApiBase() {
        override val name: String get() = "api_get_member/material"

        override fun onDataReceived(rawData: RawData) {
            val data = rawData.api_data().array ?: return
            KCDatabase.material.loadFromResponse(name, data)
        }
    }

    object basic : ApiBase() {
        override val name: String get() = "api_get_member/basic"

        override fun onDataReceived(rawData: RawData) {
            val data = rawData.api_data().obj ?: return
            KCDatabase.admiral.loadFromResponse(name, data)
        }
    }

    object ndock : ApiBase() {
        override val name: String get() = "api_get_member/ndock"

        override fun onDataReceived(rawData: RawData) {
            val data = rawData.api_data().array ?: return

            for (elem in data) {
                val id = elem["api_id"].int()
                var dock = KCDatabase.docks[id]
                if (dock == null) {
                    dock = DockData()
                    dock.loadFromResponse(name, elem)
                    KCDatabase.docks.put(dock)
                } else {
                    dock.loadFromResponse(name, elem)
                }
            }

            KCDatabase.fleets.loadFromResponse(name, data)
        }
    }

    object deck : ApiBase() {
        override val name: String get() = "api_get_member/deck"

        override fun onDataReceived(rawData: RawData) {
            val data = rawData.api_data().array ?: return
            KCDatabase.fleets.loadFromResponse(name, data)
        }
    }

    object mapinfo : ApiBase() {
        override val name: String get() = "api_get_member/mapinfo"

        override fun onDataReceived(rawData: RawData) {
            val data = rawData.api_data().obj ?: return

            // api_map_info 地图信息
            val api_map_info = data["api_map_info"].array
            if (api_map_info != null) {
                for (elem in api_map_info) {
                    val id = elem["api_id"].int()
                    var mapInfoData: MapInfoData? = KCDatabase.mapInfo[id]
                    if (mapInfoData == null) {
                        mapInfoData = MapInfoData()
                        mapInfoData.loadFromResponse(name, elem)
                        KCDatabase.mapInfo.put(mapInfoData)
                    } else {
                        mapInfoData.loadFromResponse(name, elem)
                    }
                }
            }

            // api_air_base 基地航空队 TODO 复查
            val api_air_base = data["api_air_base"].array
            if (api_air_base != null) {
                KCDatabase.baseAirCorps.clear()
                for (elem in api_air_base) {
                    val baseAirCorp = BaseAirCorpsData()
                    baseAirCorp.loadFromResponse(name, elem)
                    val array = elem["api_plane_info"].array
                    if (array != null) {
                        for (planeElem in array) {
                            val id = planeElem["api_squadron_id"].int()
                            val planeData = baseAirCorp.PlaneData(id)
                            baseAirCorp.planeInfo.put(planeData)
                        }
                    }
                    KCDatabase.baseAirCorps.put(baseAirCorp)
                }
            }
        }
    }

    object ship2 : ApiBase() {
        override val name: String get() = "api_get_member/ship2"

        override fun onDataReceived(rawData: RawData) {
            val data = JsonParser.parse(rawData.responseString).obj ?: return

            // api_data
            val shipData = data["api_data"].array
            if (shipData != null) {
                KCDatabase.ships.clear()
                for (elem in shipData) {
                    val ship = ShipData()
                    ship.loadFromResponse(name, elem)
                    KCDatabase.ships.put(ship)
                }
            }

            // api_data_deck
            val fleetData = data["api_data_deck"].array
            if (fleetData != null) KCDatabase.fleets.loadFromResponse(name, fleetData)
        }
    }

    object ship3 : ApiBase() {
        override val name: String get() = "api_get_member/ship3"

        override fun onDataReceived(rawData: RawData) {
            val data = rawData.api_data().obj ?: return

            // api_ship_data
            val api_ship_data = data["api_ship_data"].array
            if (api_ship_data != null) {
                for (elem in api_ship_data) {
                    val id = elem["api_id"].int()

                    val ship = KCDatabase.ships[id]
                    if (ship != null) {
                        ship.loadFromResponse(name, elem)

                        for (equipmentId in ship.slot) {
                            if (equipmentId == -1) continue
                            if (!KCDatabase.equipments.containsKey(equipmentId)) {
                                val newEquipment = EquipmentData()
                                val jsonObject = JsonObject()
                                jsonObject.add("api_id", JsonPrimitive(equipmentId))
                                newEquipment.loadFromResponse(name, jsonObject)
                                KCDatabase.equipments.put(newEquipment)
                            }
                        }
                    }
                }
            }

            // api_deck_data
            val fleetData = data["api_deck_data"].array
            if (fleetData != null) KCDatabase.fleets.loadFromResponse(name, fleetData)
        }
    }

    object ship_deck : ApiBase() {
        override val name: String get() = "api_get_member/ship_deck"

        override fun onDataReceived(rawData: RawData) {
            val data = rawData.api_data().obj ?: return

            //api_ship_data
            val api_ship_data = data["api_ship_data"].array
            if (api_ship_data != null) {
                for (elem in api_ship_data) {
                    val id = elem["api_id"].int()
                    var ship = KCDatabase.ships[id]
                    if (ship == null) {
                        ship = ShipData()
                        ship.loadFromResponse(name, elem)
                        KCDatabase.ships.put(ship)
                    } else {
                        ship.loadFromResponse(name, elem)
                    }
                }
            }

            //api_deck_data
            val fleetData = data["api_deck_data"].array
            if (fleetData != null) KCDatabase.fleets.loadFromResponse(name, fleetData)
        }
    }

    @Deprecated("自从6图(中部海域)陆航开放后就不用了")
    object base_air_corps : ApiBase() {
        override val name: String get() = "api_get_member/base_air_corps"

        override fun onDataReceived(rawData: RawData) {
            throw CatException("Deprecated")
        }
    }

    object questlist : ApiBase() {
        override val name: String get() = "api_get_member/questlist"

        override fun onDataReceived(rawData: RawData) {
            val requestMap = rawData.requestMap
            KCDatabase.quests.loadFromRequest(name, requestMap)

            val data = rawData.api_data().obj ?: return
            KCDatabase.quests.loadFromResponse(name, data)
        }
    }
}