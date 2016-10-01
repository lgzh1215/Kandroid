package kandroid.observer;

import java.io.File;
import java.nio.charset.Charset;
import java.util.concurrent.*;

import kandroid.data.*;

import java.util.*;

import kandroid.observer.kcsapi.*;
import kandroid.utils.Utils;

public class ApiLoader {
	
	private final static HashMap<String, ApiBase> apiMap;

    public static ApiBase get(String apiName) {
        return apiMap.get(apiName);
    }

    static {
        apiMap = new HashMap<>();
        apiMap.put(api_start2.API_NAME,new api_start2());
    }

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
		if (threadPool!=null && !threadPool.isShutdown()) {
			threadPool.execute(rawData);
		}
	}
}
