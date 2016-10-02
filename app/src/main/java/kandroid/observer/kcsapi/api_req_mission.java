package kandroid.observer.kcsapi;

import kandroid.observer.ApiBase;
import kandroid.observer.RawData;

public class api_req_mission {

    public static class start extends ApiBase {
        @Override
        protected void onDataReceived(RawData rawData) {

        }

        @Override
        public String getApiName() {
            return "api_req_mission/start";
        }
    }

    public static class result extends ApiBase {

        @Override
        protected void onDataReceived(RawData rawData) {

        }

        @Override
        public String getApiName() {
            return null;
        }
    }
}
