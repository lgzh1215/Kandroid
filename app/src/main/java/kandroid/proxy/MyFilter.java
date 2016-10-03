package kandroid.proxy;

import java.util.HashSet;

public final class MyFilter {

    private static final HashSet<String> SERVER_NAMES;
    public static final String CONTENT_TYPE_FILTER = "text/plain";
    public static final String REQUEST_BODY = "req-body";
    public static final String RESPONSE_BODY = "res-body";
    public static final int MAX_POST_FIELD_SIZE = 1024 * 1024;

    static {
        SERVER_NAMES = new HashSet<>();
        SERVER_NAMES.add("203.104.209.71");
        SERVER_NAMES.add("203.104.209.87");
        SERVER_NAMES.add("125.6.184.16");
        SERVER_NAMES.add("125.6.187.205");
        SERVER_NAMES.add("125.6.187.229");
        SERVER_NAMES.add("125.6.187.253");
        SERVER_NAMES.add("125.6.188.25");
        SERVER_NAMES.add("203.104.248.135");
        SERVER_NAMES.add("125.6.189.7");
        SERVER_NAMES.add("125.6.189.39");
        SERVER_NAMES.add("125.6.189.71");
        SERVER_NAMES.add("125.6.189.103");
        SERVER_NAMES.add("125.6.189.135");
        SERVER_NAMES.add("125.6.189.167");
        SERVER_NAMES.add("125.6.189.215");
        SERVER_NAMES.add("125.6.189.247");
        SERVER_NAMES.add("203.104.209.23");
        SERVER_NAMES.add("203.104.209.39");
        SERVER_NAMES.add("203.104.209.55");
        SERVER_NAMES.add("203.104.209.102");
        SERVER_NAMES.add("127.0.0.1");
    }

    public static boolean filterServerName(String serverName) {
        return SERVER_NAMES.contains(serverName);
    }

    public static boolean filterURI(String URI) {
        return URI.startsWith("/kcsapi");
    }

    public static boolean isNeed(String serverName, String contentType) {
        return filterServerName(serverName) && CONTENT_TYPE_FILTER.equals(contentType);
    }
}