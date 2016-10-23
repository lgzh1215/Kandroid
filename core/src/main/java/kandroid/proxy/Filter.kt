package kandroid.proxy

import java.util.*
import kotlin.text.startsWith

object Filter {

    private val SERVERS: HashMap<String, String>
    val CONTENT_TYPE_FILTER = "text/plain"
    val REQUEST_BODY = "req-body"
    val RESPONSE_BODY = "res-body"
    val MAX_POST_FIELD_SIZE = 1024 * 1024

    init {
        SERVERS = HashMap<String, String>(30)
        SERVERS.put("203.104.209.71", "横須賀鎮守府")
        SERVERS.put("203.104.209.87", "呉鎮守府")
        SERVERS.put("125.6.184.16", "佐世保鎮守府")
        SERVERS.put("125.6.187.205", "舞鶴鎮守府")
        SERVERS.put("125.6.187.229", "大湊警備府")
        SERVERS.put("125.6.187.253", "トラック泊地")
        SERVERS.put("125.6.188.25", "リンガ泊地")
        SERVERS.put("203.104.248.135", "ラバウル基地")
        SERVERS.put("125.6.189.7", "ショートランド泊地")
        SERVERS.put("125.6.189.39", "ブイン基地")
        SERVERS.put("125.6.189.71", "タウイタウイ泊地")
        SERVERS.put("125.6.189.103", "パラオ泊地")
        SERVERS.put("125.6.189.135", "ブルネイ泊地")
        SERVERS.put("125.6.189.167", "単冠湾泊地")
        SERVERS.put("125.6.189.215", "幌筵泊地")
        SERVERS.put("125.6.189.247", "宿毛湾泊地")
        SERVERS.put("203.104.209.23", "鹿屋基地")
        SERVERS.put("203.104.209.39", "岩川基地")
        SERVERS.put("203.104.209.55", "佐伯湾泊地")
        SERVERS.put("203.104.209.102", "柱島泊地")
        SERVERS.put("127.0.0.1", "")
    }

    fun filterServerName(serverName: String): Boolean {
        return SERVERS.containsKey(serverName)
    }

    fun filterURI(URI: String): Boolean {
        return URI.startsWith("/kcsapi")
    }

    fun isNeed(serverName: String, contentType: String): Boolean {
        return filterServerName(serverName) && CONTENT_TYPE_FILTER == contentType
    }
}