package kandroid.thread

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

object Threads {

    val pool: ScheduledExecutorService = Executors.newScheduledThreadPool(4)

    fun runTickTack(r: Runnable) {
        pool.scheduleAtFixedRate(r, 0, 1, TimeUnit.SECONDS)
    }
}