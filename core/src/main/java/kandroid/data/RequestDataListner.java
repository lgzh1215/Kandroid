package kandroid.data;

import kandroid.observer.RawData;

public interface RequestDataListner {
    void loadFromRequest(String apiName, RawData rawData);
}
