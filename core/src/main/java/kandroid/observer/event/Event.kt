package kandroid.observer.event

import kandroid.thread.Threads

/**
 * C#同名轮子
 */
interface Event {
    val delegates : MutableList<Delegate>

    operator fun invoke() {
        Threads.pool.execute { delegates.forEach(Delegate::onEvent) }
    }

    operator fun plusAssign(delegate: Delegate) {
        delegates.add(delegate)
    }

    operator fun minusAssign(delegate: Delegate) {
        delegates.remove(delegate)
    }

    interface Delegate {
        fun onEvent()
    }
}