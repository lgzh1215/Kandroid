package kandroid.config

import java.io.File

open class Config : IConfig {

    override val listenPort: Int get() = 8888

    override val isUseProxy: Boolean get() = false

    override val proxyPort: Int get() = 8823

    override val proxyHost: String get() = "localhost"

    override val isSaveKcsRequest: Boolean get() = true

    override val isSaveKcsResponse: Boolean get() = true

    override val storageDir: File get() = File("./")

    override val isMultipleUserMode: Boolean get() = true

    override val isDebugOn: Boolean get() = true

    companion object : IConfig {
        lateinit var config: IConfig
        val Default = Config()

        fun setDefault() {
            config = Default
        }

        override val listenPort: Int get() = config.listenPort
        override val isUseProxy: Boolean get() = config.isUseProxy
        override val proxyPort: Int get() = config.proxyPort
        override val proxyHost: String get() = config.proxyHost
        override val isSaveKcsRequest: Boolean get() = config.isSaveKcsRequest
        override val isSaveKcsResponse: Boolean get() = config.isSaveKcsResponse
        override val storageDir: File get() = config.storageDir
        override val isMultipleUserMode: Boolean get() = config.isMultipleUserMode
        override val isDebugOn: Boolean get() = config.isDebugOn

        fun getSaveKcsApiFile(fileName: String?): File {
            val file = File(storageDir, "KCAPI")
            if (fileName == null)
                return file
            else
                return File(file, fileName)
        }

        fun getSaveLogFile(fileName: String?): File {
            val file = File(storageDir, "log")
            if (fileName == null)
                return file
            else
                return File(file, fileName)
        }

        override fun toString(): String {
            return """IConfig -> ${config.javaClass.name}
│        listenPort = $listenPort
│        isUseProxy = $isUseProxy
│         proxyPort = $proxyPort
│         proxyHost = $proxyHost
│  isSaveKcsRequest = $isSaveKcsRequest
│ isSaveKcsResponse = $isSaveKcsResponse
│        storageDir = ${storageDir.canonicalPath}
│isMultipleUserMode = $isMultipleUserMode
│         isDebugOn = $isDebugOn
└──────────────────
"""
        }
    }
}