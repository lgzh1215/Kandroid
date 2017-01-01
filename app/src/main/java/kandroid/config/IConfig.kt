package kandroid.config

import java.io.File

interface IConfig {

    val listenPort: Int

    val isUseProxy: Boolean

    val proxyPort: Int

    val proxyHost: String

    val isSaveKcsApi: Boolean

    val isSaveKcsRequest: Boolean

    val isSaveKcsResponse: Boolean

    val publicStorageDir: File

    val privateStorageDir: File

    val isMultipleUserMode: Boolean

    val isDebugOn: Boolean
}