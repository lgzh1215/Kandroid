package kandroid.observer;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kandroid.config.Config;
import kandroid.observer.kcsapi.api_get_member;
import kandroid.observer.kcsapi.api_port;
import kandroid.observer.kcsapi.api_req_mission;
import kandroid.observer.kcsapi.api_req_nyukyo;
import kandroid.observer.kcsapi.api_start2;
import kandroid.utils.Utils;

public class ApiLoader {

    private static ExecutorService threadPool;

    public static void start() {
        stop();
        threadPool = Executors.newSingleThreadExecutor();
    }

    public static void stop() {
        if (threadPool != null) {
            threadPool.shutdown();
            threadPool = null;
        }
    }

    public static void load(RawData rawData) {
        if (threadPool != null && !threadPool.isShutdown()) {
            threadPool.execute(rawData);
        }
    }

    public static void save(RawData rawData) {
        if (Config.get().isSaveKcsApi()) {

            String date = null;
            try {
                if (Config.get().isSaveKcsRequest()) {
                    byte[] request = rawData.getRequest();
                    if (request != null) {
                        date = Utils.getDateString(rawData.getDate());
                        String fileName = date + "Q@" + rawData.getUri().replace('/', '@') + ".txt";
                        File file = Config.get().getSaveKcsApiFile(fileName);
                        FileUtils.writeStringToFile(file, new String(request), Charset.defaultCharset());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (Config.get().isSaveKcsResponse()) {
                    if (date == null) {
                        date = Utils.getDateString(rawData.getDate());
                    }
                    String fileName = date + "S@" + rawData.getUri().replace('/', '@') + ".json";
                    File file = Config.get().getSaveKcsApiFile(fileName);
                    FileUtils.writeStringToFile(file, rawData.toString(), Charset.defaultCharset());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static ApiBase getApi(String apiName) {
        return ApiHolder.get(apiName);
    }

    private final static class ApiHolder {
        private final static HashMap<String, ApiBase> APIS = new HashMap<>();

        public static void put(ApiBase apiBase) {
            APIS.put(apiBase.getApiName(), apiBase);
        }

        public static ApiBase get(String apiName) {
            return APIS.get(apiName);
        }
    }

    static {
        ApiHolder.put(new api_start2());

        ApiHolder.put(new api_get_member.require_info());
        ApiHolder.put(new api_get_member.slot_item());
        ApiHolder.put(new api_get_member.kdock());
        ApiHolder.put(new api_get_member.useitem());

        ApiHolder.put(new api_port.port());
        ApiHolder.put(new api_get_member.material());
        ApiHolder.put(new api_get_member.basic());
        ApiHolder.put(new api_get_member.ndock());
        ApiHolder.put(new api_get_member.deck());

        ApiHolder.put(new api_get_member.mapinfo());

        ApiHolder.put(new api_req_nyukyo.start());
        ApiHolder.put(new api_req_nyukyo.speedchange());

        ApiHolder.put(new api_req_mission.start());
        ApiHolder.put(new api_req_mission.result());

        ApiHolder.put(new api_get_member.ship3());
        ApiHolder.put(new api_get_member.ship2());

        ApiHolder.put(new api_get_member.ship_deck());
    }
}