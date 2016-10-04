package kandroid.proxy;

import java.util.HashMap;

public final class MyFilter {

    private static final HashMap<String, String> SERVERS;
    public static final String CONTENT_TYPE_FILTER = "text/plain";
    public static final String REQUEST_BODY = "req-body";
    public static final String RESPONSE_BODY = "res-body";
    public static final int MAX_POST_FIELD_SIZE = 1024 * 1024;

    static {
        SERVERS = new HashMap<>();
        SERVERS.put("203.104.209.71", "横須賀鎮守府");
        SERVERS.put("203.104.209.87", "呉鎮守府");
        SERVERS.put("125.6.184.16", "佐世保鎮守府");
        SERVERS.put("125.6.187.205", "舞鶴鎮守府");
        SERVERS.put("125.6.187.229", "大湊警備府");
        SERVERS.put("125.6.187.253", "トラック泊地");
        SERVERS.put("125.6.188.25", "リンガ泊地");
        SERVERS.put("203.104.248.135", "ラバウル基地");
        SERVERS.put("125.6.189.7", "ショートランド泊地");
        SERVERS.put("125.6.189.39", "ブイン基地");
        SERVERS.put("125.6.189.71", "タウイタウイ泊地");
        SERVERS.put("125.6.189.103", "パラオ泊地");
        SERVERS.put("125.6.189.135", "ブルネイ泊地");
        SERVERS.put("125.6.189.167", "単冠湾泊地");
        SERVERS.put("125.6.189.215", "幌筵泊地");
        SERVERS.put("125.6.189.247", "宿毛湾泊地");
        SERVERS.put("203.104.209.23", "鹿屋基地");
        SERVERS.put("203.104.209.39", "岩川基地");
        SERVERS.put("203.104.209.55", "佐伯湾泊地");
        SERVERS.put("203.104.209.102", "柱島泊地");
        SERVERS.put("127.0.0.1", "");
    }

    public static boolean filterServerName(String serverName) {
        return SERVERS.containsKey(serverName);
    }

    public static boolean filterURI(String URI) {
        return URI.startsWith("/kcsapi");
    }

    public static boolean isNeed(String serverName, String contentType) {
        return filterServerName(serverName) && CONTENT_TYPE_FILTER.equals(contentType);
    }
}