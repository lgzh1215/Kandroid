package kandroid.observer;

public abstract class ApiBase {

    protected abstract void onDataReceived(RawData rawData);

    public abstract String getApiName();
}