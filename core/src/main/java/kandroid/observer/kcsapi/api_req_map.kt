package kandroid.observer.kcsapi

import kandroid.data.KCDatabase
import kandroid.observer.ApiBase
import kandroid.observer.RawData

object api_req_map {

    object start : ApiBase() {
        override val name: String get() = "api_req_map/start"

        override fun onDataReceived(rawData: RawData) {
            KCDatabase.battle

            val data = rawData.api_data() ?: return

            KCDatabase.battle.loadFromResponse(name, data)
        }
    }
}
