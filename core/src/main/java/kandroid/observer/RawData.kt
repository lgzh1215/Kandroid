package kandroid.observer

import org.apache.commons.io.IOUtils
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URLDecoder
import java.util.*
import java.util.zip.GZIPInputStream
import kotlin.collections.dropLastWhile
import kotlin.printStackTrace
import kotlin.text.isEmpty
import kotlin.text.split
import kotlin.text.startsWith
import kotlin.text.substring

class RawData @JvmOverloads constructor(
        val uri: String,
        val request: ByteArray,
        val response: ByteArray,
        val requestMap: Map<String, String?>? = null) : Runnable {
    val date = Date()

    private fun decode(): RawData {
        if (requestMap == null && response.size > 0) {
            try {
                var uri = this.uri
                if (uri.startsWith("/kcsapi/")) {
                    uri = uri.substring(8)
                }

                val postField: Map<String, String?> = getRequestMap(
                        URLDecoder.decode(String(this.request), "UTF-8"))

                var stream: InputStream = ByteArrayInputStream(this.response)

                // Check GZIP Magic Number
                if (this.response[0] == 0x1f.toByte() && this.response[1] == 0x8b.toByte()) {
                    stream = GZIPInputStream(stream)
                }
                // Remove "svdata="
                var read: Int

//                while ((read = stream.read()) != -1 && read != '=') {
//                }

                val decodedData = org.apache.commons.io.IOUtils.toByteArray(stream)

                return RawData(uri, request, decodedData, postField)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return this
    }

    private fun getRequestMap(query: String): Map<String, String?> {
        val params = query.split("&").dropLastWhile(String::isEmpty)
        val map = HashMap<String, String?>()
        for (param in params) {
            val splited = param.split("=").dropLastWhile(String::isEmpty)
            val name = splited[0]
            var value: String? = null
            if (splited.size == 2) {
                value = splited[1]
            }
            map.put(name, value)
        }
        return map
    }

    override fun run() {
        try {
            val rawData = decode()
            ApiLoader.save(rawData)
            val api = ApiLoader.getApi(rawData.uri)
            api?.loadData(rawData)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun toString(): String {
        return String(response)
    }
}