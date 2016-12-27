package moe.lpj.kandroid.application

import android.app.Application
import android.content.res.AssetManager
import android.preference.PreferenceManager
import kandroid.observer.RawData
import kandroid.observer.kcsapi.api_start2
import moe.lpj.kandroid.R
import org.apache.commons.io.IOUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.charset.Charset

class MyApplication : Application() {

    val log: Logger = LoggerFactory.getLogger(MyApplication::class.java)

    override fun onCreate() {
        super.onCreate()
        instance = this

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
        val rawData = RawData(api_start2.name, "", string, true)
        api_start2.onDataReceived(rawData)
        log.info("载入start2完成")
    }
}