package kandroid.newdata

import com.google.gson.JsonElement

abstract class JsonWrapper {
    var data: JsonElement? = null
        private set

    fun loadFromResponse(apiName: String, responseData: JsonElement) {
        data = responseData
    }
}