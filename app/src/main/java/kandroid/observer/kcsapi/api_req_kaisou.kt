package kandroid.observer.kcsapi

import kandroid.data.KCDatabase
import kandroid.data.ShipData
import kandroid.observer.ApiBase
import kandroid.observer.RawData
import kandroid.utils.json.get
import kandroid.utils.json.int

object api_req_kaisou {

    object remodeling : ApiBase() {
        override val name: String get() = "api_req_kaisou/remodeling"

        override fun onDataReceived(rawData: RawData) {
            KCDatabase.fleets.loadFromRequest(name, rawData.requestMap)
        }
    }

    object open_exslot : ApiBase() {
        override val name: String get() = "api_req_kaisou/open_exslot"

        override fun onDataReceived(rawData: RawData) {
            val requestMap = rawData.requestMap
            val ship = KCDatabase.ships[requestMap["api_id", 0]]
            ship?.loadFromRequest(name, requestMap)
        }
    }

    object slot_deprive : ApiBase() {
        override val name: String get() = "api_req_kaisou/slot_deprive"

        override fun onDataReceived(rawData: RawData) {
            val data = rawData.api_data() ?: return

            // 获得装备的舰娘
            val api_set_ship = data["api_ship_data"]["api_set_ship"]
            val setId = api_set_ship["api_id"].int()

            // 失去装备的舰娘
            val api_unset_ship = data["api_ship_data"]["api_unset_ship"]
            val unsetId = api_unset_ship["api_id"].int()

            var setShip = KCDatabase.ships[setId]
            if (setShip == null) {
                // 貌似不会发生这种情况???
                setShip = ShipData()
                setShip.loadFromResponse(name, api_set_ship)
            } else {
                setShip.loadFromResponse(name, api_set_ship)
            }

            var unsetShip = KCDatabase.ships[unsetId]
            if (unsetShip == null) {
                unsetShip = ShipData()
                unsetShip.loadFromResponse(name, api_unset_ship)
            } else {
                unsetShip.loadFromResponse(name, api_unset_ship)
            }
        }
    }

    object slot_exchange_index : ApiBase() {
        override val name: String get() = "api_req_kaisou/slot_exchange_index"

        override fun onDataReceived(rawData: RawData) {
            val requestMap = rawData.requestMap
            val shipId = requestMap["api_id"] ?: return
            val data = rawData.api_data() ?: return
            KCDatabase.ships[shipId.toInt()]?.loadFromResponse(name, data)
        }
    }

    object powerup : ApiBase() {
        override val name: String get() = "api_req_kaisou/powerup"

        override fun onDataReceived(rawData: RawData) {
            val requestMap = rawData.requestMap
            KCDatabase.fleets.loadFromRequest(name, requestMap)

            val shipIds = requestMap["api_id_items"]!!.split(",").map(String::toInt)
            for (id in shipIds) {
                val ship = KCDatabase.ships[id]
                if (ship != null) {
                    for (equipmentId in ship.slot) {
                        if (equipmentId != -1)
                            KCDatabase.equipments.remove(equipmentId)
                    }
                }
                KCDatabase.ships.remove(id)
            }

            val data = rawData.api_data() ?: return
            val api_ship = data["api_ship"]
            val shipId = api_ship["api_id"].int()
            KCDatabase.ships[shipId]?.loadFromResponse(name, api_ship)

            KCDatabase.fleets.loadFromResponse(name, data["api_deck"])
        }
    }
}