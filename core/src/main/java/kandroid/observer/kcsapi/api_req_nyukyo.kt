package kandroid.observer.kcsapi

import kandroid.data.KCDatabase
import kandroid.observer.ApiBase
import kandroid.observer.RawData

object api_req_nyukyo {

    object start : ApiBase() {
        override val name: String get() = "api_req_nyukyo/start"

        override fun onDataReceived(rawData: RawData) {
            val requestMap = rawData.requestMap
            val shipId = requestMap["api_ship_id", 0]

            val ship = KCDatabase.ships[shipId]
            if (ship != null) {
                KCDatabase.material.fuel -= ship.repairFuel
                KCDatabase.material.steel -= ship.repairSteel

                val highSpeed = requestMap["api_highspeed", 0]
                if (highSpeed == 1) {
                    ship.repair()
                    KCDatabase.material.instantRepair--
                } else if (ship.repairTime < 60000) {
                    ship.repair()
                }
            }

            KCDatabase.fleets.loadFromRequest(name, requestMap)
        }
    }

    object speedchange : ApiBase() {
        override val name: String get() = "api_req_nyukyo/speedchange"

        override fun onDataReceived(rawData: RawData) {
            val requestMap = rawData.requestMap

            val ndockId = requestMap["api_ndock_id", 0]
            KCDatabase.docks[ndockId]?.loadFromRequest(name, requestMap)
            KCDatabase.material.instantRepair--

            KCDatabase.fleets.loadFromRequest(name, requestMap)
        }
    }
}
