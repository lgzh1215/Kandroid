package kandroid.observer.kcsapi


import kandroid.data.DockData
import kandroid.data.FleetData
import kandroid.data.ShipData
import kandroid.newdata.KCDatabase
import kandroid.observer.ApiBase
import kandroid.observer.RawData
import kandroid.utils.json.*

object api_port {
    object port : ApiBase() {
        override val apiName: String get() = "api_port/port"

        override fun onDataReceived(rawData: RawData) {
            val data = JsonParser.parse(rawData.responseString)["api_data"] ?: return

            // api_material
            val api_material = data["api_material"].array
            if (api_material != null) KCDatabase.material.loadFromResponse(apiName, api_material)

            // api_basic
            val api_basic = data["api_basic"].obj
            if (api_basic != null) KCDatabase.admiral.loadFromResponse(apiName, api_basic)

            // api_ship
            val api_ship = data["api_ship"].array
            if (api_ship != null) {
                KCDatabase.ships.clear()
                for (elem in api_ship) {
                    val ship = ShipData()
                    ship.loadFromResponse(apiName, elem)
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
                        dock.loadFromResponse(apiName, elem)
                        KCDatabase.docks.put(dock)
                    } else {
                        dock.loadFromResponse(apiName, elem)
                    }
                }
            }

            // api_deck_port
            val fleetDatas = KCDatabase.fleets.fleetDatas
            for (apiDeckPort in port.api_data.api_deck_port) {
                val id = apiDeckPort.api_id
                var fleetData: FleetData? = fleetDatas.get(id)
                if (fleetData == null) {
                    fleetData = FleetData()
                    fleetData.data = apiDeckPort
                    fleetDatas.put(fleetData)
                } else {
                    fleetData.data = apiDeckPort
                }
            }

            KCDatabase.fleets.combinedFlag = port.api_data.api_combined_flag

            //TODO
        }


    }
}