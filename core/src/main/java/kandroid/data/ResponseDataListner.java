package kandroid.data;

import kandroid.observer.RawData;

public interface ResponseDataListner {
	void loadFromResponse(String apiName, RawData rawData);
}
