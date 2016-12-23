package moe.lpj.kandroid.application

import android.app.Application
import android.preference.PreferenceManager
import moe.lpj.kandroid.R

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        PreferenceManager.setDefaultValues(this, R.xml.pref_proxy, false)
        PreferenceManager.setDefaultValues(this, R.xml.pref_debug, false)
    }

    companion object {
        lateinit var instance: MyApplication
            private set
    }
}