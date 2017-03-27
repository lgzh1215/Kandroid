package kandroid.observer.kcsapi

import kandroid.data.KCDatabase
import kandroid.observer.ApiBase
import kandroid.observer.RawData
import kandroid.utils.json.obj

object api_req_hensei {
    object change : ApiBase() {
        override val name: String get() = "api_req_hensei/change"

        override fun onDataReceived(rawData: RawData) {
            KCDatabase.fleets.loadFromRequest(name, rawData.requestMap)
        }
    }

    object combined : ApiBase() {
        override val name: String get() = "api_req_hensei/combined"

        override fun onDataReceived(rawData: RawData) {
            KCDatabase.fleets.loadFromRequest(name, rawData.requestMap)
        }
    }

    object preset_select : ApiBase() {
        override val name: String get() = "api_req_hensei/preset_select"

        override fun onDataReceived(rawData: RawData) {
            val data = rawData.api_data().obj ?: return
            KCDatabase.fleets.loadFromResponse(name, data)
        }

    }
}