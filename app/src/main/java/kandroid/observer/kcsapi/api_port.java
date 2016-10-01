package kandroid.observer.kcsapi;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kandroid.data.KCDatabase;
import kandroid.data.RawData;
import kandroid.data.ShipData;
import kandroid.observer.ApiBase;
import kandroid.utils.Utils;

import java.util.List;

public class api_port {

    public static class port extends ApiBase {

        @Override
        public void onDataReceived(RawData rawData) {
            JsonObject json = Utils.JSON_PARSER.parse(rawData.toString()).getAsJsonObject();

            // api_material
            KCDatabase.getInstance().material.loadFromResponse(getApiName(), json.get("api_material"));

            // api_basic
            KCDatabase.getInstance().admiral.loadFromResponse(getApiName(), json.get("api_basic"));

            // api_ship
            KCDatabase.getInstance().shipData.clear();
            JsonArray api_ship = json.getAsJsonArray("api_ship");
            for (JsonElement data : api_ship) {
                int id = ((JsonObject) data).get("api_id").getAsInt();
                ShipData ship = new ShipData();
                ship.loadFromResponse(getApiName(), data);
                KCDatabase.getInstance().shipData.put(id, ship);
            }

            // api_ndock


            //TODO
        }

        @Override
        public String getApiName() {
            return "api_port/port";
        }

        public static class Port {

            public int api_result;
            public String api_result_msg;
            public ApiData api_data;

            public static class ApiData {
                public ApiBasic api_basic;
                public int api_p_bgm_id;
                public int api_parallel_quest_count;
                public List<ApiMaterial> api_material;
                public List<ApiDeckPort> api_deck_port;
                public List<ApiNdock> api_ndock;
                public List<ApiShip> api_ship;
                public List<ApiLog> api_log;

                public static class ApiBasic {
                    public String api_member_id;
                    public String api_nickname;
                    public String api_nickname_id;
                    public int api_active_flag;
                    public long api_starttime;
                    public int api_level;
                    public int api_rank;
                    public int api_experience;
                    public Object api_fleetname;
                    public String api_comment;
                    public String api_comment_id;
                    public int api_max_chara;
                    public int api_max_slotitem;
                    public int api_max_kagu;
                    public int api_playtime;
                    public int api_tutorial;
                    public int api_count_deck;
                    public int api_count_kdock;
                    public int api_count_ndock;
                    public int api_fcoin;
                    public int api_st_win;
                    public int api_st_lose;
                    public int api_ms_count;
                    public int api_ms_success;
                    public int api_pt_win;
                    public int api_pt_lose;
                    public int api_pt_challenged;
                    public int api_pt_challenged_win;
                    public int api_firstflag;
                    public int api_tutorial_progress;
                    public int api_medals;
                    public int api_large_dock;
                    public List<Integer> api_furniture;
                    public List<Integer> api_pvp;
                }

                public static class ApiMaterial {
                    public int api_member_id;
                    public int api_id;
                    public int api_value;
                }

                public static class ApiDeckPort {
                    public int api_member_id;
                    public int api_id;
                    public String api_name;
                    public String api_name_id;
                    public String api_flagship;
                    public List<Integer> api_mission;
                    public List<Integer> api_ship;
                }

                public static class ApiNdock {
                    public int api_member_id;
                    public int api_id;
                    public int api_state;
                    public int api_ship_id;
                    public int api_complete_time;
                    public String api_complete_time_str;
                    public int api_item1;
                    public int api_item2;
                    public int api_item3;
                    public int api_item4;
                }

                public static class ApiShip {
                    public int api_id;
                    public int api_sortno;
                    public int api_ship_id;
                    public int api_lv;
                    public int api_nowhp;
                    public int api_maxhp;
                    public int api_leng;
                    public int api_slot_ex;
                    public int api_backs;
                    public int api_fuel;
                    public int api_bull;
                    public int api_slotnum;
                    public int api_ndock_time;
                    public int api_srate;
                    public int api_cond;
                    public int api_locked;
                    public int api_locked_equip;
                    public List<Integer> api_exp;
                    public List<Integer> api_slot;
                    public List<Integer> api_onslot;
                    public List<Integer> api_kyouka;
                    public List<Integer> api_ndock_item;
                    public List<Integer> api_karyoku;
                    public List<Integer> api_raisou;
                    public List<Integer> api_taiku;
                    public List<Integer> api_soukou;
                    public List<Integer> api_kaihi;
                    public List<Integer> api_taisen;
                    public List<Integer> api_sakuteki;
                    public List<Integer> api_lucky;
                }

                public static class ApiLog {
                    public int api_no;
                    public String api_type;
                    public String api_state;
                    public String api_message;
                }
            }
        }
    }
}
