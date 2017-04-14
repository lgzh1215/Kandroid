package kandroid.data

abstract class ApiWrapper : JsonWrapper(), RequestDataListener {
    var requestData: Map<String, String> = emptyMap()
        private set

    override fun loadFromRequest(apiName: String, requestData: MutableMap<String, String>) {
        this.requestData = requestData
    }
}