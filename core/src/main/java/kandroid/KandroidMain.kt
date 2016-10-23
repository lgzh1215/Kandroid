package kandroid

import kandroid.config.Config
import kandroid.config.IConfig
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

    fun stop() {
        ProxyServer.stop()
    }
}