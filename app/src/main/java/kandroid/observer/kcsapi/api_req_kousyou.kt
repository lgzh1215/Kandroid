package kandroid.observer.kcsapi

import kandroid.data.KCDatabase
import kandroid.observer.ApiBase
import kandroid.observer.RawData

object api_req_kousyou {
    object createship : ApiBase() {
        override val name: String get() = "api_req_kousyou/createship"

        override fun onDataReceived(rawData: RawData) {
            KCDatabase.material.loadFromRequest(name, rawData.requestMap)
        }
    }

    object createship_speedchange : ApiBase() {
        override val name: String
            get() = "api_req_kousyou/createship_speedchange"

        override fun onDataReceived(rawData: RawData) {
            val requestMap = rawData.requestMap
            val arsenal = KCDatabase.arsenals[requestMap["api_kdock_id", 0]]
            if (arsenal != null) {
                arsenal.loadFromRequest(name, requestMap)
                KCDatabase.material.instantConstruction -= if (arsenal.fuel >= 1000) 10 else 1
            }
        }
    }
}