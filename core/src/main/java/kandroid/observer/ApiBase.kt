package kandroid.observer

import com.google.gson.JsonElement
import kandroid.utils.Listenable
import kandroid.utils.json.JsonParser
import kandroid.utils.json.get
import java.util.*

abstract class ApiBase : Listenable<Any?> {
    override val listeners = ArrayList<(Any?) -> Unit>()

    abstract val name: String

    abstract fun onDataReceived(rawData: RawData)

    override fun toString(): String = name

    protected fun RawData.api_data(): JsonElement? = JsonParser.parse(this.responseString)["api_data"]
    protected operator fun Map<String, String>.get(key: String, default: Int): Int {
        return try {
            this[key]?.toInt()!!
        } catch(e: Exception) {
            default
        }
    }
}