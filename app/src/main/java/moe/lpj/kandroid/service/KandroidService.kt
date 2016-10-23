package moe.lpj.kandroid.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kandroid.KandroidMain

class KandroidService : Service() {

    override fun onCreate() {
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val intent: Intent = Intent(this, KandroidService::class.java)
        return START_STICKY
    }

    override fun onDestroy() {
        KandroidMain.stop()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}