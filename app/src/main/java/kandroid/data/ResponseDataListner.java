package kandroid.data;


import com.google.gson.JsonElement;

public interface ResponseDataListner {
	void loadFromResponse(String apiName, JsonElement data);
}
