package kandroid.observer;

import kandroid.data.RawData;

public abstract class ApiBase {

    public final void receivedData(RawData rawData) {
        onDataReceived(rawData);
    }

    protected abstract void onDataReceived(RawData rawData);

    public abstract String getApiName();
}