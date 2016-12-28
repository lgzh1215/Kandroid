package moe.lpj.kandroid.application

import android.app.Application
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.preference.PreferenceManager
import android.support.v4.app.NotificationCompat
import kandroid.KandroidMain
import moe.lpj.kandroid.R
import org.apache.commons.io.IOUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.charset.Charset

class MyApplication : Application() {

    val log: Logger = LoggerFactory.getLogger(MyApplication::class.java)

    override fun onCreate() {
        instance = this
        super.onCreate()

        PreferenceManager.setDefaultValues(this, R.xml.pref_proxy, false)
        PreferenceManager.setDefaultValues(this, R.xml.pref_debug, false)

        loadStart2Data()
    }

    companion object {
        lateinit var instance: MyApplication
            private set
    }

    fun loadStart2Data() {
        val inputStream = assets.open("api_start2.json")
        val string = IOUtils.toString(inputStream, Charset.defaultCharset())
        KandroidMain.loadStart2(string)
        log.info("载入start2完成")
    }

    fun getNotification(contentTitle: String, contentText: CharSequence, `when`: Long): Notification {
        val builder = NotificationCompat.Builder(this)
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentTitle(contentTitle)
        builder.setContentText(contentText)
        builder.setWhen(`when`)
        builder.setShowWhen(true)
        builder.setAutoCancel(true)
        return builder.build()
    }

    fun testNotification(id: Int, notification: Notification) {
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(id, notification)
    }
}