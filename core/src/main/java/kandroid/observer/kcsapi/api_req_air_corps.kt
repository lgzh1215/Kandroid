package kandroid.observer.kcsapi

import kandroid.data.BaseAirCorpsData
import kandroid.data.KCDatabase
import kandroid.observer.ApiBase
import kandroid.observer.RawData
import kandroid.utils.exception.CatException
import kandroid.utils.json.array
import kandroid.utils.json.obj
import kandroid.utils.log.Logger

object api_req_air_corps {
    object change_name : ApiBase() {
        override val name: String get() = "api_req_air_corps/change_name"

        override fun onDataReceived(rawData: RawData) {
            val requestMap = rawData.requestMap
            val id = BaseAirCorpsData.getId(requestMap)
            KCDatabase.baseAirCorps[id]?.loadFromRequest(name, requestMap)
        }
    }

    object set_action : ApiBase() {
        override val name: String get() = "api_req_air_corps/set_action"

        override fun onDataReceived(rawData: RawData) {
            val requestMap = rawData.requestMap
            val id = BaseAirCorpsData.getId(requestMap)
            KCDatabase.baseAirCorps[id]?.loadFromRequest(name, requestMap)
        }
    }

    object set_plane : ApiBase() {
        override val name: String get() = "api_req_air_corps/set_plane"

        override fun onDataReceived(rawData: RawData) {
            val data = rawData.api_data().obj ?: return

            val requestMap = rawData.requestMap
            val id = BaseAirCorpsData.getId(requestMap)

            val baseAirCorpsData = KCDatabase.baseAirCorps[id]
            if (baseAirCorpsData != null) {
                baseAirCorpsData.loadFromResponse(name, data)
                KCDatabase.material.loadFromResponse(name, data)
            }
        }
    }

    object supply : ApiBase() {
        override val name: String get() = "api_req_air_corps/supply"

        override fun onDataReceived(rawData: RawData) {
            val data = rawData.api_data().obj ?: return

            val requestMap = rawData.requestMap
            val id = BaseAirCorpsData.getId(requestMap)

            val baseAirCorpsData = KCDatabase.baseAirCorps[id]
            if (baseAirCorpsData != null) {
                baseAirCorpsData.loadFromResponse(name, data)
                KCDatabase.material.loadFromResponse(name, data)
            }
        }
    }

    object expand_base : ApiBase() {
        override val name: String get() = "api_req_air_corps/expand_base"

        override fun onDataReceived(rawData: RawData) {
            val data = rawData.api_data().array ?: return

            for (elem in data) {
                val id = BaseAirCorpsData.getId(elem)
                var baseAirCorpsData = KCDatabase.baseAirCorps[id]
                if (baseAirCorpsData == null) {
                    baseAirCorpsData = BaseAirCorpsData()
                    baseAirCorpsData.loadFromResponse(name, elem)
                    KCDatabase.baseAirCorps.put(baseAirCorpsData)
                } else {
                    Logger.e("???", CatException())
                    baseAirCorpsData.loadFromResponse(name, elem)
                }
            }
        }
    }
}