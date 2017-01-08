package kandroid

import kandroid.config.Config
import kandroid.config.IConfig
import kandroid.observer.ApiLoader
import kandroid.observer.StringRawData
import kandroid.observer.kcsapi.api_port
import kandroid.observer.kcsapi.api_start2
import kandroid.proxy.ProxyServer
import kandroid.utils.log.Logger
import org.apache.commons.io.FileUtils
import java.nio.charset.Charset

object KandroidMain {

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
        val rawData = StringRawData(api_start2.name, "", json, noSvdata = true)
        ApiLoader.load(rawData)
    }

    fun loadStart2(): Boolean {
        val file = Config.getSaveUserDataFile("api_start2")
        if (file.exists() && file.canRead()) {
            val string = FileUtils.readFileToString(file, Charset.defaultCharset())
            loadStart2(string)
            return true
        }
        return false
    }

    /**
     * 恢复用户数据
     * @param json must no "svdata="
     */
    fun loadPort(json: String) {
        val rawData = StringRawData(api_port.port.name, "", json, noSvdata = true)
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