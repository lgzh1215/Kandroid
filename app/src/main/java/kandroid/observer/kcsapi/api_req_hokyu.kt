package kandroid.observer.kcsapi

import kandroid.data.KCDatabase
import kandroid.observer.ApiBase
import kandroid.observer.RawData
import kandroid.utils.json.array
import kandroid.utils.json.get
import kandroid.utils.json.int
import kandroid.utils.json.obj

object api_req_hokyu {

    object charge : ApiBase() {
        override val name: String get() = "api_req_hokyu/charge"

        override fun onDataReceived(rawData: RawData) {
            val data = rawData.api_data().obj ?: return

            // api_ship
            val api_ship = data["api_ship"].array
            if (api_ship != null) {
                for (elem in api_ship) {
                    val id = elem["api_id"].int()
                    KCDatabase.ships[id]?.loadFromResponse(name, elem)
                }
            }

            // api_material
            val api_material = data["api_material"].array
            if (api_material != null) {
                KCDatabase.material.loadFromResponse(name, api_material)
            }
        }
    }
}