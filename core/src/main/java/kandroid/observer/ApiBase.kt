package kandroid.observer

import com.google.gson.JsonElement
import kandroid.data.KCDatabase
import kandroid.utils.json.JsonParser
import kandroid.utils.json.get
import kandroid.utils.log.Logger

import java.util.*


abstract class ApiBase {

    abstract val name: String

    abstract fun onDataReceived(rawData: RawData)

    override fun toString(): String = name

    protected fun RawData.api_data(): JsonElement = JsonParser.parse(this.responseString)["api_data"]
    protected operator fun RawData.get(key: String): JsonElement = JsonParser.parse(this.responseString)[key]
    protected operator fun Map<String, String>.get(key: String, default: Int) = this[key]?.toIntOrNull() ?: default
}