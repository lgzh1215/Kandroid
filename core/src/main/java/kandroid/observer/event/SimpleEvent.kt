package kandroid.observer.event

import java.util.*

class SimpleEvent(val name: String) : Event {
    override val delegates: MutableList<Event.Delegate> = ArrayList()
}