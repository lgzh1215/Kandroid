package kandroid.observer.kcsapi

import kandroid.data.KCDatabase
import kandroid.observer.ApiBase
import kandroid.observer.RawData

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
}