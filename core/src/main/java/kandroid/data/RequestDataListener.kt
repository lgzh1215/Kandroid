package kandroid.data

interface RequestDataListener {
    fun loadFromRequest(apiName: String, requestData: Map<String, String>)
}