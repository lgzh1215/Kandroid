package kandroid.observer.kcsapi

import kandroid.data.KCDatabase
import kandroid.observer.ApiBase
import kandroid.observer.RawData
import kandroid.utils.json.obj

object api_req_member {
    object updatecomment : ApiBase() {
        override val name: String get() = "api_req_member/updatecomment"

        override fun onDataReceived(rawData: RawData) {
            val requestMap = rawData.requestMap
            KCDatabase.admiral.loadFromRequest(name, requestMap)
        }
    }

    object updatedeckname : ApiBase() {
        override val name: String get() = "api_req_member/updatedeckname"

        override fun onDataReceived(rawData: RawData) {
            val requestMap = rawData.requestMap
            val fleetId = requestMap["api_deck_id", 0]
            KCDatabase.fleets.fleetDatas[fleetId]?.loadFromRequest(name, requestMap)
        }
    }

    object get_practice_enemyinfo : ApiBase() {
        override val name: String
            get() = "api_req_member/get_practice_enemyinfo"

        override fun onDataReceived(rawData: RawData) {
            val data = rawData.api_data().obj ?: return
            KCDatabase.battle.loadFromResponse(name, data)
        }
    }
}