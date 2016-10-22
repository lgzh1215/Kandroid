package kandroid.thread

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object Threads {

    val pool: ExecutorService = Executors.newFixedThreadPool(4)
}