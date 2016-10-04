package kandroid;

import kandroid.config.Config;
import kandroid.observer.ApiLoader;
import kandroid.proxy.MyProxyServer;
import kandroid.utils.Identifiable;

public class Test implements Identifiable {

    static void startKandroidService() {
        System.out.println(Config.get().toString());
        ApiLoader.start();
        MyProxyServer.start();
    }

    public static void main(String[] args) throws Exception {
        startKandroidService();
    }

    @Override
    public int getID() {
        return 0;
    }
}
