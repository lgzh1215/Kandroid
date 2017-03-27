package kandroid.utils.coroutine

import kandroid.observer.RawData
import kotlinx.coroutines.experimental.newSingleThreadContext
import kotlin.coroutines.experimental.AbstractCoroutineContextElement
import kotlin.coroutines.experimental.CoroutineContext

val SingleThread = newSingleThreadContext("kcsapi")

class RawDataElement(val rawData: RawData) : AbstractCoroutineContextElement(RawDataElement.Key) {
    companion object Key : CoroutineContext.Key<RawDataElement>
}