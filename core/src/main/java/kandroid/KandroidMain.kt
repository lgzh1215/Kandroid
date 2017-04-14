package kandroid

import kandroid.config.Config
import kandroid.config.FILE_START2
import kandroid.config.IConfig
import kandroid.data.KCDatabase
import kandroid.data.MapInfoData
import kandroid.data.UserData
import kandroid.observer.ApiLoader
import kandroid.observer.StringRawData
import kandroid.observer.kcsapi.api_port
import kandroid.observer.kcsapi.api_start2
import kandroid.proxy.ProxyServer
import kandroid.utils.collection.IDDictionary
import kandroid.utils.json.GSON
import kandroid.utils.json.JsonParser
import kandroid.utils.json.list
import kandroid.utils.log.Logger
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.apache.commons.io.FileUtils
import java.nio.charset.Charset
import java.util.TreeSet

object KandroidMain {

    @JvmStatic
    fun main(args: Array<String>) {
        var some: String = ""
        println(kotlin.system.measureTimeMillis {
            some = GSON.toJson(UserData(0))
        })
        println(some)
    }

    fun start() {
        ProxyServer.start()
    }

    fun updateConfig(config: IConfig?) {
        if (config == null) Config.setDefault()
        else Config.config = config
        Logger.i(Config.toString())
    }

    /**
     * 应对ACGPower不发送start2数据包，必须在获得其他api数据之前手动加载start2
     * @param json must no "svdata="
     */
    fun loadStart2(json: String) {
        val rawData = StringRawData(api_start2.name, "", json)
        ApiLoader.load(rawData)
    }

    fun loadStart2(): Boolean {
        val file = Config.getSaveUserDataFile(FILE_START2)
        if (file.exists() && file.canRead()) {
            val string = FileUtils.readFileToString(file, Charset.defaultCharset())
            loadStart2(string)
            return true
        }
        return false
    }

    fun loadPort(json: String) {
        val rawData = StringRawData(api_port.port.name, "", json, isFromKcServer = true)
        ApiLoader.load(rawData)
    }

    fun loadPort(): Boolean {
        val file = Config.getSaveUserDataFile("api_port")
        if (file.exists() && file.canRead()) {
            val string = FileUtils.readFileToString(file, Charset.defaultCharset())
            loadPort(string)
            return true
        }
        return false
    }

    fun stop() {
        ProxyServer.stop()
    }

    fun restart() {
        ProxyServer.restart()
    }
}