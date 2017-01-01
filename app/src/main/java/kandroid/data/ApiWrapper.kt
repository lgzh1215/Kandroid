package kandroid.data

import kandroid.data.JsonWrapper
import kandroid.data.RequestDataListener

abstract class ApiWrapper : JsonWrapper(), RequestDataListener {
    var requestData: Map<String, String>? = null
        private set

    override fun loadFromRequest(apiName: String, requestData: MutableMap<String, String>) {
        this.requestData = requestData
    }
}