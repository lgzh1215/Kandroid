package kandroid.config

import java.io.File
import kotlin.jvm.javaClass

open class Config {

    companion object {
        lateinit var config: Config
    }

    open val listenPort: Int
        get() = 8888

    open val isUseProxy: Boolean
        get() = false

    open val proxyPort: Int
        get() = 8823

    open val proxyHost: String
        get() = "localhost"

    open val isSaveKcsApi: Boolean
        get() = true

    open val isSaveKcsRequest: Boolean
        get() = true

    open val isSaveKcsResponse: Boolean
        get() = true

    protected open val storageDir: File
        get() = File("./")

    fun getSaveKcsApiFile(fileName: String?): File {
        val file = File(storageDir, "KCAPI")
        if (fileName == null)
            return file
        else
            return File(file, fileName)
    }

    fun getSaveDataFile(fileName: String?): File {
        val file = File(storageDir, "data")
        if (fileName == null)
            return file
        else
            return File(file, fileName)
    }

    fun getSaveUserFile(fileName: String?): File {
        val file = File(storageDir, "user")
        if (fileName == null)
            return file
        else
            return File(file, fileName)
    }

    open val isMultipleUserMode: Boolean
        get() = true

    override fun toString(): String {
        val stringBuilder = StringBuilder().append(config.javaClass.toString()).append("\n监听端口:").append(listenPort).append('\n')
        if (isUseProxy) {
            stringBuilder.append("上游代理:").append(proxyHost).append(':').append(proxyPort).append('\n')
        } else {
            stringBuilder.append("不使用代理\n")
        }
        if (isSaveKcsApi) {
            if (isSaveKcsRequest) {
                stringBuilder.append("保存Request\n")
            }
            if (isSaveKcsResponse) {
                stringBuilder.append("保存Response\n")
            }
        }
        return stringBuilder.toString()
    }
}