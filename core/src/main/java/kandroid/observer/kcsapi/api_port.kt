package kandroid.observer.kcsapi

import kandroid.data.DockData
import kandroid.data.ShipData
import kandroid.data.KCDatabase
import kandroid.observer.ApiBase
import kandroid.observer.RawData
import kandroid.utils.json.array
import kandroid.utils.json.get
import kandroid.utils.json.int
import kandroid.utils.json.obj

object api_port {

    object port : ApiBase() {
        override val name: String get() = "api_port/port"

        override fun onDataReceived(rawData: RawData) {
            val data = rawData.api_data().obj ?: return

            // api_material
            val api_material = data["api_material"].array
            if (api_material != null) KCDatabase.material.loadFromResponse(name, api_material)

            // api_basic
            val api_basic = data["api_basic"].obj
            if (api_basic != null) KCDatabase.admiral.loadFromResponse(name, api_basic)

            // api_ship
            val api_ship = data["api_ship"].array
            if (api_ship != null) {
                KCDatabase.ships.clear()
                for (elem in api_ship) {
                    val ship = ShipData()
                    ship.loadFromResponse(name, elem)
                    KCDatabase.ships.put(ship)
                }
            }

            // api_ndock
            val api_ndock = data["api_ndock"].array
            if (api_ndock != null) {
                for (elem in api_ndock) {
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
            }

            // api_deck_port
            val api_deck_port = data["api_deck_port"].array
            if (api_deck_port != null) {
                KCDatabase.fleets.loadFromResponse(name, api_deck_port)
                KCDatabase.fleets.combinedFlag = data["api_combined_flag"].int()
            }

            //TODO 基地航空隊 配置転換系の処理
        }
    }
}