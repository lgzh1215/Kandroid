package kandroid.config

import java.io.File

open class Config : IConfig {

    override val listenPort: Int get() = 8888

    override val isUseProxy: Boolean get() = true

    override val proxyPort: Int get() = 8823

    override val proxyHost: String get() = "localhost"

    override val isSaveKcsApi: Boolean get() = true

    override val isSaveKcsRequest: Boolean get() = true

    override val isSaveKcsResponse: Boolean get() = true

    override val publicStorageDir: File get() = File("./")

    override val privateStorageDir: File get() = publicStorageDir

    override val isMultipleUserMode: Boolean get() = true

    override val isDebugOn: Boolean get() = true

    companion object : IConfig {
        lateinit var config: IConfig

        fun setDefault() {
            config = Config()
        }

        override val listenPort: Int get() = config.listenPort
        override val isUseProxy: Boolean get() = config.isUseProxy
        override val proxyPort: Int get() = config.proxyPort
        override val proxyHost: String get() = config.proxyHost
        override val isSaveKcsApi: Boolean get() = config.isSaveKcsApi
        override val isSaveKcsRequest: Boolean get() = config.isSaveKcsRequest
        override val isSaveKcsResponse: Boolean get() = config.isSaveKcsResponse
        override val publicStorageDir: File get() = config.publicStorageDir
        override val privateStorageDir: File get() = config.privateStorageDir
        override val isMultipleUserMode: Boolean get() = config.isMultipleUserMode
        override val isDebugOn: Boolean get() = config.isDebugOn

        fun getSaveKcsApiFile(fileName: String?): File = getFile("KCAPI", fileName, private = false)

        fun getSaveLogFile(fileName: String?): File = getFile("log", fileName, private = false)

        fun getSaveUserDataFile(fileName: String?): File = getFile("data", fileName, private = !isDebugOn)

        private fun getFile(folderName: String, fileName: String?, private: Boolean): File {
            val file = File(if (private) privateStorageDir else publicStorageDir, folderName)
            return if (fileName == null) file else File(file, fileName)
        }

        override fun toString(): String {
            return """${config.javaClass.name}
│        listenPort = $listenPort
│        isUseProxy = $isUseProxy
│         proxyPort = $proxyPort
│         proxyHost = $proxyHost
│      isSaveKcsApi = $isSaveKcsApi
│  isSaveKcsRequest = $isSaveKcsRequest
│ isSaveKcsResponse = $isSaveKcsResponse
│  publicStorageDir = ${publicStorageDir.canonicalPath}
│ privateStorageDir = ${privateStorageDir.canonicalPath}
│isMultipleUserMode = $isMultipleUserMode
│         isDebugOn = $isDebugOn
└──────────────────
"""
        }
    }
}