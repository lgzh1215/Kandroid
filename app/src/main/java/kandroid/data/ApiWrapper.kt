package kandroid.data

abstract class ApiWrapper : JsonWrapper(), RequestDataListener {
    var requestData: Map<String, String>? = null
        private set

    override fun loadFromRequest(apiName: String, requestData: MutableMap<String, String>) {
        this.requestData = requestData
    }
}