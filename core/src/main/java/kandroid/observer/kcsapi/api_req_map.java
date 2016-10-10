package kandroid.observer.kcsapi;

import kandroid.observer.ApiBase;
import kandroid.observer.RawData;

public class api_req_map {

    public static class start extends ApiBase {

        public static final String API_NAME = "api_req_map/start";

        @Override
        protected void onDataReceived(RawData rawData) {

        }

        @Override
        public String getApiName() {
            return API_NAME;
        }

        public static class Start {

            public int api_result;
            public String api_result_msg;
            public ApiData api_data;

            public static class ApiData {
                public int api_rashin_flg;
                public int api_rashin_id;
                public int api_maparea_id;
                public int api_mapinfo_no;
                public int api_no;
                public int api_color_no;
                public int api_event_id;
                public int api_event_kind;
                public int api_next;
                public int api_bosscell_no;
                public int api_bosscomp;
                public ApiAirsearch api_airsearch;
                public int api_from_no;

                public static class ApiAirsearch {
                    public int api_plane_type;
                    public int api_result;
                }
            }
        }
    }
}
