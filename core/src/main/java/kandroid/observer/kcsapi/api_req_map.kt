package kandroid.observer.kcsapi

import kandroid.observer.ApiBase
import kandroid.observer.RawData

object api_req_map {

    object start : ApiBase() {
        override val name: String get() = "api_req_map/start"

        override fun onDataReceived(rawData: RawData) {
            //TODO
        }
    }
}
