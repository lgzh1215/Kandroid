package kandroid.config;

public class Config {
	
	private static Config config;

	public static void setConfig(Config config) {
		Config.config = config;
	}

	public static Config get() {
		if (config == null)
			config = new Config();
		return config;
	}

    public int getListenPort() {
        return 8888;
    }
	
	public boolean isUseProxy() {
		return true;
	}

    public int getProxyPort() {
        return 8823;
    }

    public String getProxyHost() {
        return "localhost";
    }

    public boolean isSaveKcsApi() {
        return true;
    }
	
	public boolean isSaveKcsRequest() {
        return true;
    }
	
	public boolean isSaveKcsResponse() {
        return true;
    }
	
	public String getStorageDir() {
		return null;
	}
}
