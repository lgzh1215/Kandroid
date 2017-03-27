package kandroid.data

interface RequestDataListener {
    fun loadFromRequest(apiName: String, requestData: MutableMap<String, String>)

    operator fun Map<String, String>.get(key: String, default: Int) = try {
        this[key]?.toInt()!!
    } catch(e: Exception) {
        default
    }
}