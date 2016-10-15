package kandroid.observer.newkcsapi

import kandroid.observer.NewApiBase
import kandroid.observer.RawData
import kandroid.utils.json.JsonParser

object api_start2 : NewApiBase() {
    override val apiName: String get() = "api_start2"

    override fun onDataReceived(rawData: RawData) {
        val json = JsonParser.parse(rawData.responseString)

    }
}