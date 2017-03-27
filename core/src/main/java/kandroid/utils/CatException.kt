package kandroid.utils

class CatException : RuntimeException {
    constructor() : super() {
    }

    constructor(detailMessage: String) : super(detailMessage) {
    }

    constructor(detailMessage: String, throwable: Throwable) : super(detailMessage, throwable) {
    }

    constructor(throwable: Throwable) : super(throwable) {
    }
}