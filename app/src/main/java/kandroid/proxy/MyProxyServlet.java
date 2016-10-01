package kandroid.proxy;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpProxy;
import org.eclipse.jetty.client.ProxyConfiguration;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.proxy.AsyncProxyServlet;
import org.eclipse.jetty.util.Callback;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kandroid.config.Config;
import kandroid.observer.RawData;
import kandroid.observer.ApiLoader;

public class MyProxyServlet extends AsyncProxyServlet {

    @Override
    protected void sendProxyRequest(HttpServletRequest clientRequest, HttpServletResponse proxyResponse,
                                    Request proxyRequest) {
        proxyRequest.onRequestContent(new RequestContentListener(clientRequest));
        super.sendProxyRequest(clientRequest, proxyResponse, proxyRequest);
    }

    @Override
    protected void onResponseContent(HttpServletRequest request, HttpServletResponse response, Response proxyResponse,
                                     byte[] buffer, int offset, int length, Callback callback) {
        if (MyFilter.isNeed(request.getServerName(), response.getContentType())) {
            ByteArrayOutputStream stream = (ByteArrayOutputStream) request.getAttribute(MyFilter.RESPONSE_BODY);
            if (stream == null) {
                stream = new ByteArrayOutputStream();
                request.setAttribute(MyFilter.RESPONSE_BODY, stream);
            }
            stream.write(buffer, offset, length);
        }
        super.onResponseContent(request, response, proxyResponse, buffer, offset, length, callback);
    }

    @Override
    protected void onProxyResponseSuccess(HttpServletRequest request, HttpServletResponse response,
                                          Response proxyResponse) {
        System.out.println(request.getServerName() + " | " + request.getRequestURI());
        if (MyFilter.isNeed(request.getServerName(), response.getContentType())) {
            ByteArrayOutputStream stream = (ByteArrayOutputStream) request.getAttribute(MyFilter.RESPONSE_BODY);
            if (stream != null) {
                byte[] postField = (byte[]) request.getAttribute(MyFilter.REQUEST_BODY);
                try {
                    System.out.println(URLDecoder.decode(new String(postField),"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                RawData data = new RawData(request.getRequestURI(), postField, stream.toByteArray());
                ApiLoader.load(data);
            }
        }
        super.onProxyResponseSuccess(request, response, proxyResponse);
    }

    @Override
    protected HttpClient newHttpClient() {
        HttpClient client = super.newHttpClient();
        if (Config.get().isUseProxy()) {
            String host = Config.get().getProxyHost();
            int port = Config.get().getProxyPort();
            List<ProxyConfiguration.Proxy> proxies = client.getProxyConfiguration().getProxies();
            proxies.add(new HttpProxy(host, port));
        }
        return client;
    }

    private final class RequestContentListener implements Request.ContentListener {

        private final HttpServletRequest httpRequest;

        RequestContentListener(HttpServletRequest request) {
            this.httpRequest = request;
        }

        @Override
        public void onContent(Request request, ByteBuffer buffer) {
            if (((buffer.limit() > 0) && (buffer.limit() <= MyFilter.MAX_POST_FIELD_SIZE))
                    && MyFilter.filterServerName(request.getHost())) {
                this.httpRequest.setAttribute(MyFilter.REQUEST_BODY, Arrays.copyOf(buffer.array(), buffer.limit()));
            }
        }
    }
}
