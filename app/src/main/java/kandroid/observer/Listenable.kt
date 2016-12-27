package kandroid.observer

interface Listenable {
    val listeners: MutableList<() -> Unit>

    fun addListener(listener: () -> Unit) = listeners.add(listener)
    fun removeListener(listener: () -> Unit) = listeners.remove(listener)

    fun notifyListeners() = listeners.forEach { it.invoke() }
}
