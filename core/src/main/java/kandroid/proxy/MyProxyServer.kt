package kandroid.proxy

import kandroid.config.Config
import org.apache.commons.lang3.StringUtils
import org.eclipse.jetty.proxy.ConnectHandler
import org.eclipse.jetty.server.Connector
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import java.net.BindException
import java.util.concurrent.TimeUnit

object MyProxyServer {

    var server: Server? = null

    private var host: String? = null
    private var port: Int = 0
    private var proxyHost: String? = null
    private var proxyPort: Int = 0

    fun start() {
        try {
            server = Server()
            updateSetting()
            setConnector()

            val connectHandler = ConnectHandler()
            server!!.handler = connectHandler

            val servletContextHandler = ServletContextHandler(
                    connectHandler, "/", ServletContextHandler.SESSIONS)
            val holder = ServletHolder(MyProxyServlet())
            holder.setInitParameter("idleTimeout", TimeUnit.MINUTES.toMillis(2).toString())
            holder.setInitParameter("timeout", TimeUnit.MINUTES.toMillis(2).toString())
            servletContextHandler.addServlet(holder, "/*")

            try {
                server!!.start()
            } catch (e: Exception) {
                if (e is BindException) {
                }//端口被占用
                e.printStackTrace()
            }

        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    private fun setConnector() {
        //SSL Support
        val connector = ServerConnector(server)
        connector.port = port
        connector.host = host
        server!!.connectors = arrayOf<Connector>(connector)
    }

    private fun updateSetting(): Boolean {
        val newHost = "localhost"
        val newPort = Config.listenPort

        var newProxyHost: String? = null
        var newProxyPort = 0
        if (Config.isUseProxy) {
            newProxyHost = Config.proxyHost
            newProxyPort = Config.proxyPort
        }

        if (StringUtils.equals(newHost, host) && newProxyPort == proxyPort &&
                StringUtils.equals(newProxyHost, proxyHost) && newPort == port) {
            return false
        }

        host = newHost
        port = newPort
        proxyHost = newProxyHost
        proxyPort = newProxyPort
        return true
    }

    fun restart() {
        try {
            if (server == null) return
            if (updateSetting()) {
                server!!.stop()
                setConnector()
                server!!.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun end() {
        try {
            if (server != null) {
                server!!.stop()
                server!!.join()
                server = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
