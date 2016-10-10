package kandroid.proxy

import kandroid.config.Config
import org.apache.commons.lang3.StringUtils
import org.eclipse.jetty.proxy.ConnectHandler
import org.eclipse.jetty.server.Connector
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import java.lang.Long
import java.net.BindException
import java.util.concurrent.TimeUnit
import kotlin.printStackTrace

object MyProxyServer {

    var server: org.eclipse.jetty.server.Server? = null

    private var host: String? = null
    private var port: Int = 0
    private var proxyHost: String? = null
    private var proxyPort: Int = 0

    fun start() {
        try {
            server = org.eclipse.jetty.server.Server()
            updateSetting()
            setConnector()

            val connectHandler = org.eclipse.jetty.proxy.ConnectHandler()
            server!!.handler = connectHandler

            val servletContextHandler = org.eclipse.jetty.servlet.ServletContextHandler(
                    connectHandler, "/", org.eclipse.jetty.servlet.ServletContextHandler.SESSIONS)
            val holder = org.eclipse.jetty.servlet.ServletHolder(MyProxyServlet())
            holder.setInitParameter("idleTimeout", java.lang.Long.toString(TimeUnit.MINUTES.toMillis(2)))
            holder.setInitParameter("timeout", java.lang.Long.toString(TimeUnit.MINUTES.toMillis(2)))
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
        val connector = org.eclipse.jetty.server.ServerConnector(server)
        connector.port = port
        connector.host = host
        server!!.connectors = arrayOf<org.eclipse.jetty.server.Connector>(connector)
    }

    private fun updateSetting(): Boolean {
        val newHost = "localhost"
        val newPort = Config.config.listenPort

        var newProxyHost: String? = null
        var newProxyPort = 0
        if (Config.config.isUseProxy) {
            newProxyHost = Config.config.proxyHost
            newProxyPort = Config.config.proxyPort
        }

        if (org.apache.commons.lang3.StringUtils.equals(newHost, host) && newProxyPort == proxyPort &&
                org.apache.commons.lang3.StringUtils.equals(newProxyHost, proxyHost) && newPort == port) {
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
