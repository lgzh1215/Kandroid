package kandroid.data

import com.google.gson.JsonElement
import kandroid.utils.json.jsonNull

abstract class JsonWrapper : ResponseDataListener {
    var data: JsonElement = jsonNull

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        this.data = responseData
    }
}