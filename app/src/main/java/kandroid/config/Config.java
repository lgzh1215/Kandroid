package kandroid.config;

import java.io.File;

import kandroid.utils.Utils;

public class Config {

    private static class ConfigHolder {
        static Config config = null;
    }

    public static void setConfig(Config config) {
        ConfigHolder.config = Utils.requireNonNull(config);
    }

    public static Config get() {
        return Utils.requireNonNull(ConfigHolder.config);
    }

    public int getListenPort() {
        return 8888;
    }

    public boolean isUseProxy() {
        return false;
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

    protected File getStorageDir() {
        return new File("./");
    }

    public final File getSaveKcsApiFile(String fileName) {
        File file = new File(getStorageDir(), "KCAPI");
        if (fileName == null)
            return file;
        else
            return new File(file, fileName);
    }

    public final File getSaveDataFile(String fileName) {
        File file = new File(getStorageDir(), "data");
        if (fileName == null)
            return file;
        else
            return new File(file, fileName);
    }

    public final File getSaveUserFile(String fileName) {
        File file = new File(getStorageDir(), "user");
        if (fileName == null)
            return file;
        else
            return new File(file, fileName);
    }

    public boolean isMultipleUserMode() {
        return true;
    }

    @Override
    public final String toString() {
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