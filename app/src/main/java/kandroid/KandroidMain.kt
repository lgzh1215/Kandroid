package kandroid

import kandroid.config.Config
import kandroid.config.IConfig
import kandroid.observer.RawData
import kandroid.observer.kcsapi.api_start2
import kandroid.proxy.ProxyServer
import kandroid.utils.log.Logger

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
        val rawData = RawData(api_start2.name, "", json, true)
        api_start2.onDataReceived(rawData)
    }

    fun stop() {
        ProxyServer.stop()
    }
}