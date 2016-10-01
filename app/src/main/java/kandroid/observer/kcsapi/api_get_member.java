package kandroid.observer.kcsapi;

import com.google.gson.JsonParser;
import kandroid.observer.*;
import kandroid.data.*;

public class api_get_member {

    public static class require_info extends ApiBase {

        @Override
        protected void onDataReceived(RawData rawData) {
            // TODO: Implement this method
        }

        @Override
        public String getApiName() {
            return "api_get_member/require_info";
        }
    }

    public static class basic extends ApiBase {

        @Override
        protected void onDataReceived(RawData rawData) {
            KCDatabase.getInstance().admiral.loadFromResponse(getApiName(), new JsonParser().parse(rawData.toString()));
        }

        @Override
        public String getApiName() {
            return "api_get_member/basic";
        }

    }

    public static class slot_item extends ApiBase {

        @Override
        protected void onDataReceived(RawData rawData) {
            // TODO: Implement this method
        }

        @Override
        public String getApiName() {
            return "api_get_member/slot_item";
        }
    }

    public static class useitem extends ApiBase {

        @Override
        protected void onDataReceived(RawData rawData) {
            // TODO: Implement this method
        }

        @Override
        public String getApiName() {
            return "api_get_member/useitem";
        }
    }
}
