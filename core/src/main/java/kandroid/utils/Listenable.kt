package kandroid.utils


interface Listenable<T> {
    val listeners : MutableList<(T) -> Unit>

    fun addListener(t: (T) -> Unit) = listeners.add(t)

    fun removeListener(t: (T) -> Unit) = listeners.remove(t)
}