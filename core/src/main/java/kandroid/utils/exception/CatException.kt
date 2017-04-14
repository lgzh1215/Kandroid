package kandroid.utils.exception

typealias Cat = CatException

class CatException : RuntimeException {
    constructor() : super()

    constructor(detailMessage: String?) : super(detailMessage)

    constructor(detailMessage: String, throwable: Throwable) : super(detailMessage, throwable)

    constructor(throwable: Throwable) : super(throwable)

    @Suppress("NOTHING_TO_INLINE")
    companion object {
        inline fun shouldNotHappen(detailMessage: String = ""): Nothing = throw CatException(detailMessage + "????")
        inline fun error(detailMessage: String): Nothing = throw CatException(detailMessage)
    }
}