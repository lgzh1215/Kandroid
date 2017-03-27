package kandroid.data

import com.google.gson.JsonElement

interface ResponseDataListener {
    fun loadFromResponse(apiName: String, responseData: JsonElement)
}