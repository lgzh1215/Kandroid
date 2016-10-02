package kandroid.observer;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class RawData implements Runnable {

    private final String uri;
    private final byte[] request;
    private final byte[] response;
    private final Map<String, String> requestMap;
    private final Date date = new Date();

    public RawData(String uri, byte[] request, byte[] response) {
        this(uri, request, response, null);
    }

    public RawData(String uri, byte[] request, byte[] response, Map<String, String> requestMap) {
        this.uri = uri;
        this.request = request;
        this.response = response;
        this.requestMap = requestMap;
    }

    public String getUri() {
        return uri;
    }

    public byte[] getRequest() {
        return request;
    }

    public byte[] getResponse() {
        return response;
    }

    public Map<String, String> getRequestMap() {
        return requestMap;
    }

    public Date getDate() {
        return date;
    }

    private RawData decode() {
        if (response.length > 0) {
            try {
                String uri = this.uri;
                if (uri.startsWith("/kcsapi/")) {
                    uri = uri.substring(8);
                }

                Map<String, String> postField = null;
                if (this.request != null) {
                    postField = getRequestMap(URLDecoder.decode(new String(this.request).trim(), "UTF-8"));
                }

                InputStream stream = new ByteArrayInputStream(this.response);

                // Check GZIP Magic Number
                if ((this.response[0] == (byte) 0x1f) && (this.response[1] == (byte) 0x8b)) {
                    stream = new GZIPInputStream(stream);
                }
                // Remove "svdata="
                int read;
                while (((read = stream.read()) != -1) && (read != '=')) {
                }

                byte[] decodedData = IOUtils.toByteArray(stream);

                return new RawData(uri, request, decodedData, postField) {
                    @Override
                    public String toString() {
                        return new String(getResponse());
                    }
                };
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    private static Map<String, String> getRequestMap(String query) {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<>();
        for (String param : params) {
            String[] splited = param.split("=");
            String name = splited[0];
            String value = null;
            if (splited.length == 2) {
                value = splited[1];
            }
            map.put(name, value);
        }
        return map;
    }

    @Override
    public void run() {
        try {
            RawData rawData = decode();
            ApiLoader.save(rawData);
            ApiBase api = ApiLoader.getApi(rawData.getUri());
            if (api != null) api.onDataReceived(rawData);
            String s;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}