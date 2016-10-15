package kandroid.newdata

abstract class ApiWrapper : JsonWrapper(), RequestDataListener {
    var requestData: Map<String, String>? = null
        private set

    override fun loadFromRequest(apiName: String, requestData: Map<String, String>) {
        this.requestData = requestData
    }
}