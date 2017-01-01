package moe.lpj.kandroid.kandroid

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import kandroid.config.IConfig
import moe.lpj.kandroid.utils.isExternalStorageWritable
import java.io.File


object ConfigA {

    fun get(context: Context): IConfig {
        val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        return object : IConfig {
            override val privateStorageDir: File
                get() = throw UnsupportedOperationException() //TODO
            val _listenPort: Int = pref.getString("listenPort", "-1").toInt()
            val _isUseProxy: Boolean = pref.getBoolean("isUseProxy", false)
            val _proxyPort: Int = pref.getString("proxyPort", "-1").toInt()
            val _proxyHost: String = pref.getString("proxyHost", "null")
            val _isSaveKcsApi: Boolean = pref.getBoolean("isSaveKcsApi", false)
            val _isSaveKcsRequest: Boolean = pref.getBoolean("isSaveKcsRequest", false)
            val _isSaveKcsResponse: Boolean = pref.getBoolean("isSaveKcsResponse", false)
            val _storageDir: File = if (isExternalStorageWritable()) context.getExternalFilesDir(null) else context.filesDir
            val _isDebugOn: Boolean = pref.getBoolean("isDebugOn", false)

            override val listenPort: Int get() = _listenPort
            override val isUseProxy: Boolean get() = _isUseProxy
            override val proxyPort: Int get() = _proxyPort
            override val proxyHost: String get() = _proxyHost
            override val isSaveKcsApi: Boolean get() = _isSaveKcsApi
            override val isSaveKcsRequest: Boolean get() = _isSaveKcsRequest
            override val isSaveKcsResponse: Boolean get() = _isSaveKcsResponse
            override val publicStorageDir: File get() = _storageDir
            override val isMultipleUserMode: Boolean get() = false
            override val isDebugOn: Boolean get() = _isDebugOn
        }
    }
}