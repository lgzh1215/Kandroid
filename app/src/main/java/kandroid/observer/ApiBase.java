package kandroid.observer;

public abstract class ApiBase {

    public final void loadData(RawData rawData) {
        onDataReceived(rawData);
    }

    protected abstract void onDataReceived(RawData rawData);

    public abstract String getApiName();
}