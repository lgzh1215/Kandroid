package kandroid.observer

import kandroid.utils.log.Logger
import org.apache.commons.io.IOUtils
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.net.URLDecoder
import java.util.*
import java.util.zip.GZIPInputStream

class RawData @JvmOverloads constructor(
        val uri: String,
        val request: ByteArray,
        val response: ByteArray,
        val isReady: Boolean = false) {

    constructor(uri: String,
                request: String,
                response: String,
                isReady: Boolean) : this(uri, request.toByteArray(), response.toByteArray(), isReady)

    val date = Date()

    fun decode(): RawData {
        if (isReady && response.isEmpty()) return this
        try {
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

            val decodedData = IOUtils.toByteArray(stream)

            return RawData(uri, request, decodedData, true)
        } catch (e: Exception) {
            Logger.e("处理数据时出错 -> $e", e)
            return this
        }
    }

    val requestString: String = String(request)

    val requestMap: Map<String, String> get() {
        val map = HashMap<String, String>()
        val params = URLDecoder.decode(requestString, "UTF-8").split("&")
        var name: String
        var value: String
        var split: List<String>
        for (param in params) {
            split = param.split("=")
            if (split.size == 2) {
                name = split[0]
                value = split[1]
                map.put(name, value)
            }
        }
        return map
    }

    val responseString: String = String(response)


//    override fun toString(): String {
//        return String(response)
//    }
}