package kandroid.newdata

abstract class ApiWrapper : JsonWrapper() {
    var requestData: Map<String, String>? = null
        private set

    fun loadFromRequest(apiName: String, requestData: Map<String, String>) {
        this.requestData = requestData
    }
}