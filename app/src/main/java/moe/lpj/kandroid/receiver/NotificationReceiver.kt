package moe.lpj.kandroid.receiver

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import moe.lpj.kandroid.utils.NotificationUtils

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        System.err.println("NotificationReceiver:onReceive")
        NotificationUtils.registerSimpleNotification(intent)
    }
}
