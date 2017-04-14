package kandroid.observer

import kandroid.config.Config
import kandroid.utils.log.Logger
import kandroid.utils.yyyyMMdd_HHmmssSSS
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.net.URLDecoder
import java.nio.charset.Charset
import java.util.*
import java.util.zip.GZIPInputStream

abstract class RawData(val uri: String,
                       val date: Date = Date(),
                       val isFromKcServer: Boolean) {

    abstract val requestString: String

    abstract val responseString: String

    val requestMap: MutableMap<String, String> by lazy {
        val map = HashMap<String, String>()
        val params = URLDecoder.decode(requestString, "UTF-8").split("&")
        for (param in params) {
            val split = param.split("=")
            if (split.size == 2) {
                map.put(split[0], split[1])
            }
        }
        map
    }

    open fun decode(): RawData = this

    fun saveToFile() {
        if (!isFromKcServer) return

        if (Config.isSaveKcsApi) {
            val dateString: String = date.yyyyMMdd_HHmmssSSS
            try {
                if (Config.isSaveKcsRequest) {
                    val fileName = "${dateString}Q@${uri.replace('/', '@')}.txt"
                    val file = Config.getSaveKcsApiFile(fileName)
                    FileUtils.writeStringToFile(file, requestString, Charset.defaultCharset())
                }
            } catch (e: Exception) {
                Logger.e("保存Request失败 -> $e", e)
            }

            try {
                if (Config.isSaveKcsResponse) {
                    val fileName = "${dateString}S@${uri.replace('/', '@')}.json"
                    val file = Config.getSaveKcsApiFile(fileName)
                    FileUtils.writeStringToFile(file, responseString, Charset.defaultCharset())
                }
            } catch (e: Exception) {
                Logger.e("保存Response失败 -> $e", e)
            }
        }
    }
}

class ByteArrayRawData(uri: String,
                       val request: ByteArray,
                       val response: ByteArray,
                       date: Date = Date(),
                       isFromKcServer: Boolean = true) : RawData(uri, date, isFromKcServer) {

    override fun decode(): RawData {
        if (response.isEmpty()) return this

        var uri = this.uri
        if (uri.startsWith("/kcsapi/")) {
            uri = uri.substring(8)
        }

        var stream: InputStream = ByteArrayInputStream(this.response)

        // Check GZIP Magic Number
        if (this.response[0] == 0x1f.toByte() && this.response[1] == 0x8b.toByte()) {
            stream = GZIPInputStream(stream)
        }

        // Remove "svdata="
        var read: Int
        do {
            read = stream.read()
        } while (read != -1 && read != '='.toInt())

        val decodedData = IOUtils.toString(stream, Charset.defaultCharset())

        return StringRawData(uri, String(request), decodedData, date, isFromKcServer)
    }

    override val requestString: String by lazy { String(request) }

    override val responseString: String by lazy { String(response) }
}

class StringRawData(uri: String,
                    request: String,
                    response: String,
                    date: Date = Date(),
                    isFromKcServer: Boolean = false) : RawData(uri, date, isFromKcServer) {

    override fun decode(): RawData {
        if (responseString.isEmpty()) return this

        var uri = this.uri
        if (uri.startsWith("/kcsapi/")) {
            uri = uri.substring(8)
        }

        var responseString2: String = responseString

        var stream: GZIPInputStream? = null
        // Check GZIP Magic Number
        if (this.responseString[0] == 31.toChar()) {
            stream = GZIPInputStream(ByteArrayInputStream(responseString.toByteArray()))
        }

        // Remove "svdata="
        if (stream != null) {
            var read: Int
            do {
                read = stream.read()
            } while (read != -1 && read != '='.toInt())

            responseString2 = IOUtils.toString(stream, Charset.defaultCharset())
        } else {
            if (responseString.startsWith("svdata="))
                responseString2 = responseString.substring(7)
        }

        return StringRawData(uri, requestString, responseString2, date, isFromKcServer)
    }

    override val requestString: String = request

    override val responseString: String = response
}