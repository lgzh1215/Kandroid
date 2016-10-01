package kandroid.data;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.zip.*;
import kandroid.observer.*;
import kandroid.utils.*;
import org.apache.commons.io.*;

public class RawData implements Runnable {

    private final String uri;
    private final byte[] request;
    private final byte[] response;
    private final Map<String, String> postField;

    public RawData(String uri, byte[] request, byte[] response) {
        this.uri = uri;
        this.request = request;
        this.response = response;
        this.postField = null;
    }

    public RawData(String uri, Map<String, String> postField, byte[] response) {
        this.uri = uri;
        this.request = null;
        this.response = response;
        this.postField = postField;
    }

    public String getUri() {
        return uri;
    }

    public byte[] getResponse() {
        return response;
    }

    public RawData decode() {
        if (response.length > 0) {
            try {
                Map<String, String> field = null;
                if (this.request != null) {
                    field = getQueryMap(URLDecoder.decode(new String(this.request).trim(), "UTF-8"));
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

                return new RawData(getUri().substring(8), field, decodedData) {
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

    private static Map<String, String> getQueryMap(String query) {
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
		RawData rawData = decode();
		ApiBase api = ApiLoader.get(rawData.getUri());
		Utils.requireNonNull(api).receivedData(rawData);
	}
}
