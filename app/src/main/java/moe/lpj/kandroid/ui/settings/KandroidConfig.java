package moe.lpj.kandroid.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.File;

import kandroid.config.Config;

public class KandroidConfig extends Config {

    private SharedPreferences preferences;

    public KandroidConfig(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public int getListenPort() {
        return preferences.getInt("listen_port", 8888);
    }

    @Override
    public boolean isUseProxy() {
        return super.isUseProxy();
    }

    @Override
    public int getProxyPort() {
        return super.getProxyPort();
    }

    @Override
    public String getProxyHost() {
        return super.getProxyHost();
    }

    @Override
    public boolean isSaveKcsApi() {
        return super.isSaveKcsApi();
    }

    @Override
    public boolean isSaveKcsRequest() {
        return super.isSaveKcsRequest();
    }

    @Override
    public boolean isSaveKcsResponse() {
        return super.isSaveKcsResponse();
    }

    @Override
    protected File getStorageDir() {
        return super.getStorageDir();
    }

    @Override
    public boolean isMultipleUserMode() {
        return super.isMultipleUserMode();
    }
}
