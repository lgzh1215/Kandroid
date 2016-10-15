package kandroid.observer.kcsapi

import kandroid.observer.ApiBase
import kandroid.observer.RawData

object api_req_nyukyo {

    object start : ApiBase() {
        override val name: String get() = "api_req_nyukyo/start"

        override fun onDataReceived(rawData: RawData) {
//            val requestMap = rawData.requestMap
//            val shipId = Integer.valueOf(requestMap["api_ship_id"])!!
//            val highSpeed = Integer.valueOf(requestMap["api_highspeed"])!!
//
//
//            val ship = KCDatabase.ships.get(shipId)
//            KCDatabase.material.fuel = KCDatabase.material.fuel - ship.repairFuel
//            KCDatabase.material.steel = KCDatabase.material.steel - ship.repairSteel
//            if (highSpeed == 1) {
//                ship.repair()
//                KCDatabase.material.instantRepair = KCDatabase.material.instantRepair - 1
//            } else if (ship.repairTime < 60000) {
//                ship.repair()
//            }
//
//            for (fleetData in KCDatabase.fleets.fleetDatas) {
//                fleetData.loadFromRequest(name, rawData)
//            }
        }

    }

    object speedchange : ApiBase() {
        override val name: String get() = "api_req_nyukyo/speedchange"

        override fun onDataReceived(rawData: RawData) {
//            val requestMap = rawData.requestMap
//            val ndockId = Integer.valueOf(requestMap["api_ndock_id"])
//
//            val dockData = KCDatabase.dockData.get(ndockId!!)
//            if (dockData.state == 1 && dockData.getShipID() !== 0) {
//                KCDatabase.ships.get(dockData.getShipID()).repair()
//                dockData.data!!.setApi_state(0)
//                dockData.data!!.setApi_ship_id(0)
//            }
//
//            KCDatabase.material.instantRepair = KCDatabase.material.instantRepair - 1
//
//            for (fleetData in KCDatabase.fleets.fleetDatas) {
//                fleetData.loadFromRequest(name, rawData)
//            }
        }

    }
}
