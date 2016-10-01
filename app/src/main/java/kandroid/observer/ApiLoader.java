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
        return ApiHolder.APIS.get(apiName);
    }

    private final static class ApiHolder {
        final static HashMap<String, ApiBase> APIS;

        static {
            APIS = new HashMap<>();
            APIS.put(api_start2.API_NAME, new api_start2());

            APIS.put(api_get_member.require_info.API_NAME, new api_get_member.require_info());
            APIS.put(api_get_member.slot_item.API_NAME, new api_get_member.slot_item());
            APIS.put(api_get_member.kdock.API_NAME, new api_get_member.kdock());
            APIS.put(api_get_member.useitem.API_NAME, new api_get_member.useitem());

            APIS.put(api_port.port.API_NAME, new api_port.port());
            APIS.put(api_get_member.material.API_NAME, new api_get_member.material());
            APIS.put(api_get_member.basic.API_NAME, new api_get_member.basic());
            APIS.put(api_get_member.ndock.API_NAME, new api_get_member.ndock());
            APIS.put(api_get_member.deck.API_NAME, new api_get_member.deck());
        }
    }
}