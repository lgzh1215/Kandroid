package kandroid.proxy

import kandroid.config.Config
import kandroid.observer.ApiLoader
import kandroid.observer.RawData
import kandroid.thread.Threads
import org.apache.commons.io.output.ByteArrayOutputStream
import org.eclipse.jetty.client.HttpClient
import org.eclipse.jetty.client.HttpProxy
import org.eclipse.jetty.client.api.Request
import org.eclipse.jetty.client.api.Response
import org.eclipse.jetty.proxy.AsyncProxyServlet
import org.eclipse.jetty.util.Callback
import java.nio.ByteBuffer
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ProxyServlet : AsyncProxyServlet() {

    override fun sendProxyRequest(clientRequest: HttpServletRequest, proxyResponse: HttpServletResponse,
                                  proxyRequest: Request) {
        proxyRequest.onRequestContent(RequestContentListener(clientRequest))
        super.sendProxyRequest(clientRequest, proxyResponse, proxyRequest)
    }

    override fun onResponseContent(request: HttpServletRequest, response: HttpServletResponse, proxyResponse: Response,
                                   buffer: ByteArray, offset: Int, length: Int, callback: Callback) {
        if (Filter.isNeed(request.serverName, response.contentType)) {
            var stream = request.getAttribute(Filter.RESPONSE_BODY) as? ByteArrayOutputStream
            if (stream == null) {
                stream = ByteArrayOutputStream()
                request.setAttribute(Filter.RESPONSE_BODY, stream)
            }
            stream.write(buffer, offset, length)
        }
        super.onResponseContent(request, response, proxyResponse, buffer, offset, length, callback)
    }

    override fun onProxyResponseSuccess(request: HttpServletRequest, response: HttpServletResponse,
                                        proxyResponse: Response) {
        if (Filter.isNeed(request.serverName, response.contentType)) {
            val stream = request.getAttribute(Filter.RESPONSE_BODY) as? ByteArrayOutputStream
            if (stream != null) {
                val postField = request.getAttribute(Filter.REQUEST_BODY) as ByteArray

                val data = RawData(request.requestURI, postField, stream.toByteArray())
                Threads.pool.execute { ApiLoader.load(data) }
            }
        }
        super.onProxyResponseSuccess(request, response, proxyResponse)
    }

    override fun newHttpClient(): HttpClient {
        val client = super.newHttpClient()
        if (Config.isUseProxy) {
            val host = Config.proxyHost
            val port = Config.proxyPort
            val proxies = client.proxyConfiguration.proxies
            proxies.add(HttpProxy(host, port))
        }
        return client
    }

    private class RequestContentListener(val httpRequest: HttpServletRequest) : Request.ContentListener {

        override fun onContent(request: Request, buffer: ByteBuffer) {
            if (buffer.limit() in 1..Filter.MAX_POST_FIELD_SIZE && Filter.filterServerName(request.host)) {
                httpRequest.setAttribute(Filter.REQUEST_BODY, Arrays.copyOf(buffer.array(), buffer.limit()))
            }
        }
    }
}
