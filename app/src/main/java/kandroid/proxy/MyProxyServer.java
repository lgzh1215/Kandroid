package kandroid.proxy;

import kandroid.config.Config;
import kandroid.observer.ApiLoader;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.proxy.ConnectHandler;
import org.eclipse.jetty.proxy.ProxyServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.concurrent.TimeUnit;
import java.net.*;
import java.io.*;

public class MyProxyServer {

    public static void main(String[] args) {
        start();
        ApiLoader.start();
    }

    public static Server server;

    private static String host;
    private static int port;
    private static String proxyHost;
    private static int proxyPort;

    public static void start() {
        try {
            server = new Server();
            updateSetting();
            setConnector();

            ConnectHandler connectHandler = new ConnectHandler();
            server.setHandler(connectHandler);

            ServletContextHandler servletContextHandler = new ServletContextHandler(
                    connectHandler, "/", ServletContextHandler.SESSIONS);
            ServletHolder holder = new ServletHolder(new MyProxyServlet());
            holder.setInitParameter("idleTimeout", Long.toString(TimeUnit.MINUTES.toMillis(2)));
            holder.setInitParameter("timeout", Long.toString(TimeUnit.MINUTES.toMillis(2)));
            servletContextHandler.addServlet(holder, "/*");

            try {
                server.start();
            } catch (Exception e) {
                if (e instanceof BindException) {
                }//端口被占用
                e.printStackTrace();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void setConnector() {
        //SSL Support
        ServerConnector connector = new ServerConnector(server);
//        ServerConnector connector = new ServerConnector(server, new SslContextFactory());
        connector.setPort(port);
        connector.setHost(host);
        server.setConnectors(new Connector[]{connector});
    }

    private static boolean updateSetting() {
        String newHost = "localhost";
        int newPort = Config.get().getListenPort();

        String newProxyHost = null;
        int newProxyPort = 0;
        if (Config.get().isUseProxy()) {
            newProxyHost = Config.get().getProxyHost();
            newProxyPort = Config.get().getProxyPort();
        }

        if (StringUtils.equals(newHost, host) && (newProxyPort == proxyPort) &&
                StringUtils.equals(newProxyHost, proxyHost) && (newPort == port)) {
            return false;
        }

        host = newHost;
        port = newPort;
        proxyHost = newProxyHost;
        proxyPort = newProxyPort;
        return true;
    }

    public static void restart() {
        try {
            if (server == null) {
                return;
            }
            if (updateSetting()) {
                server.stop();
                setConnector();
                server.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void end() {
        try {
            if (server != null) {
                server.stop();
                server.join();
                server = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
