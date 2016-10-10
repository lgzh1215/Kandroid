package kandroid.observer

abstract class ApiBase {

    fun loadData(rawData: RawData) {
        onDataReceived(rawData)
    }

    protected abstract fun onDataReceived(rawData: RawData)

    abstract val apiName: String
}