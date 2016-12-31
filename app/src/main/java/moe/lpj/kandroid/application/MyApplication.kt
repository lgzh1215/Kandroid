package moe.lpj.kandroid.application

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.support.v4.app.NotificationCompat
import kandroid.KandroidMain
import kandroid.thread.Threads
import moe.lpj.kandroid.R
import moe.lpj.kandroid.kandroid.ConfigA
import moe.lpj.kandroid.service.ProxyService
import moe.lpj.kandroid.viewmodel.DockViewModel
import moe.lpj.kandroid.viewmodel.MissionViewModel
import moe.lpj.kandroid.viewmodel.viewUpdater
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

        MissionViewModel
        DockViewModel

        KandroidMain.updateConfig(ConfigA.get(this))
        loadStart2Data()
        loadPortData()
        KandroidMain.start()

        Threads.runTickTack(viewUpdater)

        startService(Intent(this, ProxyService::class.java))
    }

    companion object {
        lateinit var instance: MyApplication
            private set
    }

    fun loadStart2Data() {
        val ok = KandroidMain.loadStart2()
        if (!ok) {
            val inputStream = assets.open("api_start2.json")
            val string = IOUtils.toString(inputStream, Charset.defaultCharset())
            KandroidMain.loadStart2(string)
        }
        log.info("载入start2完成")
    }

    fun loadPortData() {
        val ok = KandroidMain.loadPort()
        if (ok)
            log.info("载入port完成")
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