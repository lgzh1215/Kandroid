package kandroid.newdata

interface RequestDataListener {
    fun loadFromRequest(apiName: String, requestData: Map<String, String>)
}