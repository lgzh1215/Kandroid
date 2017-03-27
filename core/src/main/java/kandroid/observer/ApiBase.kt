package kandroid.observer

import com.google.gson.JsonElement
import kandroid.utils.json.JsonParser
import kandroid.utils.json.get
import kandroid.utils.log.Logger

import java.util.*

abstract class ApiBase : Listenable {

    override val listeners = ArrayList<() -> Unit>()

    override fun addListener(listener: () -> Unit): Boolean {
        Logger.i("$name Get Listener $listener")
        return super.addListener(listener)
    }

    override fun removeListener(listener: () -> Unit): Boolean {
        Logger.i("$name Remove Listener $listener")
        return super.removeListener(listener)
    }

    override fun notifyListeners() {
        Logger.i("$name Notify ${listeners.size} Listeners")
        super.notifyListeners()
    }

    abstract val name: String

    abstract fun onDataReceived(rawData: RawData)

    open fun directLoad(stringRawData: StringRawData) {}

    override fun toString(): String = name

    protected fun RawData.api_data(): JsonElement = JsonParser.parse(this.responseString)["api_data"]
    protected operator fun Map<String, String>.get(key: String, default: Int): Int {
        return try {
            this[key]?.toInt()!!
        } catch(e: Exception) {
            default
        }
    }
}