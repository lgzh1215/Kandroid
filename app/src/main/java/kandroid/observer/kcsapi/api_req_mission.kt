package kandroid.observer.kcsapi

import kandroid.observer.ApiBase
import kandroid.observer.RawData

object api_req_mission {

    object start : ApiBase() {
        override val name: String get() = "api_req_mission/start"

        override fun onDataReceived(rawData: RawData) {
        }
    }

    object result : ApiBase() {
        override val name: String get() = "api_req_mission/result"

        override fun onDataReceived(rawData: RawData) {
        }
    }
}
