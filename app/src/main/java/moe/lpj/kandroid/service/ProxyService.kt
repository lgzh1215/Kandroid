package moe.lpj.kandroid.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class ProxyService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        isRunning = true
        return Service.START_STICKY
    }

    override fun onDestroy() {
        isRunning = false
    }

    companion object {
        var isRunning = false
    }
}