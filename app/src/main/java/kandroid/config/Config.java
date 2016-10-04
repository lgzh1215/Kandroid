package kandroid.config;

import java.io.File;

import kandroid.utils.Utils;

public class Config {

    private static class ConfigHolder {
        static Config config = new Config();
    }

    public static void setConfig(Config config) {
        ConfigHolder.config = config;
    }

    public static Config get() {
        return ConfigHolder.config;
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

    public File getSaveKcsApiFile(String fileName) {
        return new File("./KCAPI/" + Utils.requireNonNull(fileName));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append(get().getClass().toString())
                .append("\n监听端口:").append(getListenPort()).append('\n');
        if (isUseProxy()) {
            stringBuilder.append("上游代理:").append(getProxyHost()).append(':').append(getProxyPort()).append('\n');
        } else {
            stringBuilder.append("不使用代理\n");
        }
        if (isSaveKcsApi()) {
            if (isSaveKcsRequest()) {
                stringBuilder.append("保存Request\n");
            }
            if (isSaveKcsResponse()) {
                stringBuilder.append("保存Response\n");
            }
        }
        return stringBuilder.toString();
    }
}