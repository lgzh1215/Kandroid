package kandroid.observer

import com.google.gson.JsonElement
import kandroid.utils.json.JsonParser
import kandroid.utils.json.get

abstract class ApiBase {
    abstract val name: String

    abstract fun onDataReceived(rawData: RawData)

    override fun toString(): String = name

    protected fun RawData.api_data(): JsonElement? = JsonParser.parse(this.responseString)["api_data"]
}