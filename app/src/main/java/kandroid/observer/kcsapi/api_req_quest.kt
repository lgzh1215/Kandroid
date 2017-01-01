package kandroid.observer.kcsapi

import kandroid.data.KCDatabase
import kandroid.observer.ApiBase
import kandroid.observer.RawData

object api_req_quest {
    object clearitemget : ApiBase() {
        override val name: String get() = "api_req_quest/clearitemget"

        override fun onDataReceived(rawData: RawData) {
            val requestMap = rawData.requestMap
            KCDatabase.quests.loadFromRequest(name, requestMap)
        }
    }

    object stop : ApiBase() {
        override val name: String get() = "api_req_quest/stop"

        override fun onDataReceived(rawData: RawData) {
            val requestMap = rawData.requestMap
            KCDatabase.quests.loadFromRequest(name, requestMap)
        }
    }
}