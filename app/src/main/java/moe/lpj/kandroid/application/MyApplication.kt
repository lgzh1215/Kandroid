package moe.lpj.kandroid.application

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.preference.PreferenceManager
import android.support.v4.app.NotificationCompat
import kandroid.KandroidMain
import moe.lpj.kandroid.R
import moe.lpj.kandroid.kandroid.ConfigA
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
        PreferenceManager.setDefaultValues(this, R.xml.pref_notification, false)

        Thread {
            loadStart2Data()
            KandroidMain.updateConfig(ConfigA.get(this))
            KandroidMain.start()
        }.start()
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

    fun getNotificationBuilder(): NotificationCompat.Builder {
        return NotificationCompat.Builder(this)
    }

    fun getNotificationManager(): NotificationManager {
        return getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun getAlarmManager(): AlarmManager {
        return getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }
}