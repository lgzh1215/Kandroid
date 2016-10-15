package kandroid.observer

abstract class NewApiBase {
    abstract val apiName: String

    abstract fun onDataReceived(rawData: RawData)
}