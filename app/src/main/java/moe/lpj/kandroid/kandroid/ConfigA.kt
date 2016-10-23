package moe.lpj.kandroid.kandroid

import kandroid.config.IConfig
import java.io.File

class ConfigA : IConfig {

    companion object {
        fun get() {}
    }

    override val listenPort: Int
        get() = throw UnsupportedOperationException()
    override val isUseProxy: Boolean
        get() = throw UnsupportedOperationException()
    override val proxyPort: Int
        get() = throw UnsupportedOperationException()
    override val proxyHost: String
        get() = throw UnsupportedOperationException()
    override val isSaveKcsApi: Boolean
        get() = throw UnsupportedOperationException()
    override val isSaveKcsRequest: Boolean
        get() = throw UnsupportedOperationException()
    override val isSaveKcsResponse: Boolean
        get() = throw UnsupportedOperationException()
    override val storageDir: File
        get() = throw UnsupportedOperationException()
    override val isMultipleUserMode: Boolean
        get() = throw UnsupportedOperationException()
    override val isDebugOn: Boolean
        get() = throw UnsupportedOperationException()
}