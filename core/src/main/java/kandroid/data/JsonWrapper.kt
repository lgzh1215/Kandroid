package kandroid.data

import com.google.gson.JsonElement

abstract class JsonWrapper : ResponseDataListener {
    var data: JsonElement? = null
        private set

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        this.data = responseData
    }
}