package kandroid.observer.kcsapi

import kandroid.data.ArsenalData
import kandroid.data.EquipmentData
import kandroid.data.KCDatabase
import kandroid.data.ShipData
import kandroid.observer.ApiBase
import kandroid.observer.RawData
import kandroid.utils.json.*

object api_req_kousyou {

    @Deprecated("之后会有api_get_member/material来更新资源数据")
    object createship : ApiBase() {
        override val name: String get() = "api_req_kousyou/createship"

        override fun onDataReceived(rawData: RawData) {
            KCDatabase.material.loadFromRequest(name, rawData.requestMap)
        }
    }

    object createship_speedchange : ApiBase() {
        override val name: String get() = "api_req_kousyou/createship_speedchange"

        override fun onDataReceived(rawData: RawData) {
            val requestMap = rawData.requestMap
            val arsenal = KCDatabase.arsenals[requestMap["api_kdock_id", 0]]
            if (arsenal != null) {
                arsenal.loadFromRequest(name, requestMap)
                KCDatabase.material.instantConstruction -= if (arsenal.fuel >= 1000) 10 else 1
            }
        }
    }

    object destroyship : ApiBase() {
        override val name: String get() = "api_req_kousyou/destroyship"

        override fun onDataReceived(rawData: RawData) {
            val requestMap = rawData.requestMap
            KCDatabase.fleets.loadFromRequest(name, requestMap)

            val shipId = requestMap["api_ship_id", 0]
            val ship = KCDatabase.ships[shipId]
            if (ship != null) {
                for (equipmentId in ship.slot) {
                    if (equipmentId != -1)
                        KCDatabase.equipments.remove(equipmentId)
                }
            }
            KCDatabase.ships.remove(shipId)

            val data = rawData.api_data() ?: return
            val api_material = data["api_material"]
            KCDatabase.material.loadFromResponse(name, api_material)
        }
    }

    object destroyitem2 : ApiBase() {
        override val name: String get() = "api_req_kousyou/destroyitem2"

        override fun onDataReceived(rawData: RawData) {
            val requestMap = rawData.requestMap
            requestMap["api_slotitem_ids"]!!.split(",")
                    .map(String::toInt).forEach { KCDatabase.equipments.remove(it) }

            val data = rawData.api_data() ?: return
            val api_get_material = data["api_get_material"]
            KCDatabase.material.loadFromResponse(name, api_get_material)
        }
    }

    object remodel_slot : ApiBase() {
        override val name: String get() = "api_req_kousyou/remodel_slot"

        override fun onDataReceived(rawData: RawData) {
//            val requestMap = rawData.requestMap
//            val equipmentId = requestMap["api_slot_id", 0]

            val data = rawData.api_data() ?: return
            val api_after_material = data["api_after_material"]
            KCDatabase.material.loadFromResponse(name, api_after_material)

            val api_after_slot = data["api_after_slot"]
            if (api_after_slot !== jsonNull) {
                // 改修成功
                val equipmentId = api_after_slot["api_id"].int()
                KCDatabase.equipments[equipmentId]?.loadFromResponse(name, api_after_slot)
            }

            val api_use_slot_id = data["api_use_slot_id"].array
            if (api_use_slot_id != null) {
                for (elem in api_use_slot_id) {
                    KCDatabase.equipments.remove(elem.int())
                }
            }
        }
    }

    object getship : ApiBase() {
        override val name: String get() = "api_req_kousyou/getship"

        override fun onDataReceived(rawData: RawData) {
            val data = rawData.api_data() ?: return

            // api_kdock
            val api_kdock = data["api_kdock"].array
            if (api_kdock != null) {
                for (elem in api_kdock) {
                    val id = elem["api_id"].int()
                    var arsenal = KCDatabase.arsenals[id]
                    if (arsenal == null) {
                        arsenal = ArsenalData()
                        arsenal.loadFromResponse(name, elem)
                        KCDatabase.arsenals.put(arsenal)
                    } else {
                        arsenal.loadFromResponse(name, elem)
                    }
                }
            }

            // api_slotitem
            val api_slotitem = data["api_slotitem"].array
            if (api_slotitem != null) {
                for (elem in api_slotitem) {
                    val equipment = EquipmentData()
                    equipment.loadFromResponse(name, elem)
                    KCDatabase.equipments.put(equipment)
                }
            }

            // api_ship
            val api_ship = data["api_ship"].obj
            if (api_ship != null) {
                val ship = ShipData()
                ship.loadFromResponse(name, api_ship)
                KCDatabase.ships.put(ship)
            }
        }
    }

    object createitem : ApiBase() {
        override val name: String get() = "api_req_kousyou/createitem"

        override fun onDataReceived(rawData: RawData) {
            val data = rawData.api_data() ?: return

            if (data["api_create_flag"].int() != 0) {
                // 开发成功
                val api_slot_item = data["api_slot_item"].obj
                if (api_slot_item != null) {
                    val equipment = EquipmentData()
                    equipment.loadFromResponse(name, api_slot_item)
                    KCDatabase.equipments.put(equipment)
                }
            }

            val api_material = data["api_material"]
            KCDatabase.material.loadFromResponse(name, api_material)
        }
    }
}