package kandroid.observer.kcsapi;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import kandroid.data.KCDatabase;
import kandroid.data.RawData;
import kandroid.observer.ApiBase;
import kandroid.observer.POJO;
import kandroid.utils.Identifiable;

import java.util.List;

public class api_start2 extends ApiBase {
	
	public static final String API_NAME = "api_start2";

    @Override
    protected void onDataReceived(RawData rawData) {
        ApiStart2 apiStart2 = new Gson().fromJson(rawData.toString(), ApiStart2.class);
        KCDatabase.getInstance().master.setApiStart2(apiStart2);
    }

    @Override
    public String getApiName() {
        return API_NAME;
    }
	
	public static abstract class IdentifiablePOJO extends POJO implements Identifiable {
	}

    public static class ApiStart2 {

        public ApiData api_data;
        public int api_result;
        public String api_result_msg;

        public static class ApiData {
            public ApiMstConst api_mst_const;
            public ApiMstItemShop api_mst_item_shop;
            public List<ApiMstBgm> api_mst_bgm;
            public List<Integer> api_mst_equip_exslot;
            public List<ApiMstFurniture> api_mst_furniture;
            public List<ApiMstFurnituregraph> api_mst_furnituregraph;
            public List<ApiMstMaparea> api_mst_maparea;
            public List<ApiMstMapbgm> api_mst_mapbgm;
            public List<ApiMstMapcell> api_mst_mapcell;
            public List<ApiMstMapinfo> api_mst_mapinfo;
            public List<ApiMstMission> api_mst_mission;
            public List<ApiMstPayitem> api_mst_payitem;
            public List<ApiMstShip> api_mst_ship;
            public List<ApiMstShipgraph> api_mst_shipgraph;
            public List<ApiMstShipupgrade> api_mst_shipupgrade;
            public List<ApiMstSlotitem> api_mst_slotitem;
            public List<ApiMstSlotitemEquiptype> api_mst_slotitem_equiptype;
            public List<ApiMstStype> api_mst_stype;
            public List<ApiMstUseitem> api_mst_useitem;

            public static class ApiMstConst {
                public ApiBokoMaxShips api_boko_max_ships;
                public ApiDpflagQuest api_dpflag_quest;
                public ApiParallelQuestMax api_parallel_quest_max;

                public static class ApiBokoMaxShips {
                    public int api_int_value;
                    public String api_string_value;
                }

                public static class ApiDpflagQuest {
                    public int api_int_value;
                    public String api_string_value;
                }

                public static class ApiParallelQuestMax {
                    public int api_int_value;
                    public String api_string_value;
                }
            }

            public static class ApiMstItemShop {
                public List<Integer> api_cabinet_1;
                public List<Integer> api_cabinet_2;
            }

            public static class ApiMstBgm {
                public int api_id;
                public String api_name;
            }

            public static class ApiMstFurniture {
                public String api_description;
                public int api_id;
                public int api_no;
                public int api_price;
                public int api_rarity;
                public int api_saleflg;
                public int api_season;
                public String api_title;
                public int api_type;
            }

            public static class ApiMstFurnituregraph {
                public String api_filename;
                public int api_id;
                public int api_no;
                public int api_type;
                public String api_version;
            }

            public static class ApiMstMaparea {
                public int api_id;
                public String api_name;
                public int api_type;
            }

            public static class ApiMstMapbgm {
                public int api_id;
                public int api_maparea_id;
                public int api_no;
                public List<Integer> api_boss_bgm;
                public List<Integer> api_map_bgm;
            }

            public static class ApiMstMapcell {
                public int api_color_no;
                public int api_id;
                public int api_map_no;
                public int api_maparea_id;
                public int api_mapinfo_no;
                public int api_no;
            }

            public static class ApiMstMapinfo extends IdentifiablePOJO {
                public int api_id;
                public String api_infotext;
                public int api_level;
                public int api_maparea_id;
                public String api_name;
                public int api_no;
                public String api_opetext;
                public List<Integer> api_item;
                public List<Integer> api_sally_flag;
                public int api_required_defeat_count;

                @Override
                public int getID() {
                    return api_id;
                }
            }

            public static class ApiMstMission extends IdentifiablePOJO {
                public String api_details;
                public int api_difficulty;
                public int api_id;
                public int api_maparea_id;
                public String api_name;
                public int api_return_flag;
                public int api_time;
                public int api_use_bull;
                public double api_use_fuel;
                public List<Integer> api_win_item1;
                public List<Integer> api_win_item2;

                @Override
                public int getID() {
                    return api_id;
                }
            }

            public static class ApiMstPayitem {
                public String api_description;
                public int api_id;
                public String api_name;
                public int api_price;
                public int api_type;
                public List<Integer> api_item;
            }

            public static class ApiMstShip extends IdentifiablePOJO{
                public int api_afterbull;
                public int api_afterfuel;
                public int api_afterlv;
                public String api_aftershipid;
                public int api_backs;
                public int api_buildtime;
                public int api_bull_max;
                public int api_fuel_max;
                public String api_getmes;
                public int api_id;
                public int api_leng;
                public String api_name;
                public int api_slot_num;
                public int api_soku;
                public int api_sortno;
                public int api_stype;
                public int api_voicef;
                public String api_yomi;
                public List<Integer> api_broken;
                public List<Integer> api_houg;
                public List<Integer> api_luck;
                public List<Integer> api_maxeq;
                public List<Integer> api_powup;
                public List<Integer> api_raig;
                public List<Integer> api_souk;
                public List<Integer> api_taik;
                public List<Integer> api_tyku;

                @Override
                public int getID() {
                    return api_id;
                }
            }

            public static class ApiMstShipgraph {
                public String api_filename;
                public int api_id;
                public int api_sortno;
                public List<Integer> api_battle_d;
                public List<Integer> api_battle_n;
                public List<Integer> api_boko_d;
                public List<Integer> api_boko_n;
                public List<Integer> api_ensyue_n;
                public List<Integer> api_ensyuf_d;
                public List<Integer> api_ensyuf_n;
                public List<Integer> api_kaisyu_d;
                public List<Integer> api_kaisyu_n;
                public List<Integer> api_kaizo_d;
                public List<Integer> api_kaizo_n;
                public List<Integer> api_map_d;
                public List<Integer> api_map_n;
                public List<String> api_version;
                public List<Integer> api_weda;
                public List<Integer> api_wedb;
            }

            public static class ApiMstShipupgrade {
                public int api_catapult_count;
                public int api_current_ship_id;
                public int api_drawing_count;
                public int api_id;
                public int api_original_ship_id;
                public int api_sortno;
                public int api_upgrade_level;
                public int api_upgrade_type;
            }

            public static class ApiMstSlotitem extends IdentifiablePOJO {
                public int api_atap;
                public int api_bakk;
                public int api_baku;
                public int api_houg;
                public int api_houk;
                public int api_houm;
                public int api_id;
                public String api_info;
                public int api_leng;
                public int api_luck;
                public String api_name;
                public int api_raig;
                public int api_raik;
                public int api_raim;
                public int api_rare;
                public int api_sakb;
                public int api_saku;
                public int api_soku;
                public int api_sortno;
                public int api_souk;
                public int api_taik;
                public int api_tais;
                public int api_tyku;
                public String api_usebull;
                public List<Integer> api_broken;
                public List<Integer> api_type;
                public int api_cost;
                public int api_distance;

                @Override
                public int getID() {
                    return api_id;
                }
            }

            public static class ApiMstSlotitemEquiptype extends IdentifiablePOJO{
                public int api_id;
                public String api_name;
                public int api_show_flg;

                @Override
                public int getID() {
                    return api_id;
                }
            }

            public static class ApiMstStype extends IdentifiablePOJO {
                public ApiEquipType api_equip_type;
                public int api_id;
                public int api_kcnt;
                public String api_name;
                public int api_scnt;
                public int api_sortno;

                @Override
                public int getID() {
                    return api_id;
                }

                public static class ApiEquipType {
                    @SerializedName("1")
                    public int value1;
                    @SerializedName("10")
                    public int value10;
                    @SerializedName("11")
                    public int value11;
                    @SerializedName("12")
                    public int value12;
                    @SerializedName("13")
                    public int value13;
                    @SerializedName("14")
                    public int value14;
                    @SerializedName("15")
                    public int value15;
                    @SerializedName("16")
                    public int value16;
                    @SerializedName("17")
                    public int value17;
                    @SerializedName("18")
                    public int value18;
                    @SerializedName("19")
                    public int value19;
                    @SerializedName("2")
                    public int value2;
                    @SerializedName("20")
                    public int value20;
                    @SerializedName("21")
                    public int value21;
                    @SerializedName("22")
                    public int value22;
                    @SerializedName("23")
                    public int value23;
                    @SerializedName("24")
                    public int value24;
                    @SerializedName("25")
                    public int value25;
                    @SerializedName("26")
                    public int value26;
                    @SerializedName("27")
                    public int value27;
                    @SerializedName("28")
                    public int value28;
                    @SerializedName("29")
                    public int value29;
                    @SerializedName("3")
                    public int value3;
                    @SerializedName("30")
                    public int value30;
                    @SerializedName("31")
                    public int value31;
                    @SerializedName("32")
                    public int value32;
                    @SerializedName("33")
                    public int value33;
                    @SerializedName("34")
                    public int value34;
                    @SerializedName("35")
                    public int value35;
                    @SerializedName("36")
                    public int value36;
                    @SerializedName("37")
                    public int value37;
                    @SerializedName("38")
                    public int value38;
                    @SerializedName("39")
                    public int value39;
                    @SerializedName("4")
                    public int value4;
                    @SerializedName("40")
                    public int value40;
                    @SerializedName("41")
                    public int value41;
                    @SerializedName("42")
                    public int value42;
                    @SerializedName("43")
                    public int value43;
                    @SerializedName("44")
                    public int value44;
                    @SerializedName("45")
                    public int value45;
                    @SerializedName("46")
                    public int value46;
                    @SerializedName("47")
                    public int value47;
                    @SerializedName("48")
                    public int value48;
                    @SerializedName("49")
                    public int value49;
                    @SerializedName("5")
                    public int value5;
                    @SerializedName("50")
                    public int value50;
                    @SerializedName("51")
                    public int value51;
                    @SerializedName("52")
                    public int value52;
                    @SerializedName("53")
                    public int value53;
                    @SerializedName("54")
                    public int value54;
                    @SerializedName("55")
                    public int value55;
                    @SerializedName("56")
                    public int value56;
                    @SerializedName("57")
                    public int value57;
                    @SerializedName("58")
                    public int value58;
                    @SerializedName("59")
                    public int value59;
                    @SerializedName("6")
                    public int value6;
                    @SerializedName("60")
                    public int value60;
                    @SerializedName("61")
                    public int value61;
                    @SerializedName("62")
                    public int value62;
                    @SerializedName("63")
                    public int value63;
                    @SerializedName("64")
                    public int value64;
                    @SerializedName("65")
                    public int value65;
                    @SerializedName("66")
                    public int value66;
                    @SerializedName("67")
                    public int value67;
                    @SerializedName("68")
                    public int value68;
                    @SerializedName("69")
                    public int value69;
                    @SerializedName("7")
                    public int value7;
                    @SerializedName("70")
                    public int value70;
                    @SerializedName("71")
                    public int value71;
                    @SerializedName("72")
                    public int value72;
                    @SerializedName("73")
                    public int value73;
                    @SerializedName("74")
                    public int value74;
                    @SerializedName("75")
                    public int value75;
                    @SerializedName("76")
                    public int value76;
                    @SerializedName("77")
                    public int value77;
                    @SerializedName("78")
                    public int value78;
                    @SerializedName("79")
                    public int value79;
                    @SerializedName("8")
                    public int value8;
                    @SerializedName("80")
                    public int value80;
                    @SerializedName("81")
                    public int value81;
                    @SerializedName("82")
                    public int value82;
                    @SerializedName("83")
                    public int value83;
                    @SerializedName("84")
                    public int value84;
                    @SerializedName("85")
                    public int value85;
                    @SerializedName("86")
                    public int value86;
                    @SerializedName("87")
                    public int value87;
                    @SerializedName("88")
                    public int value88;
                    @SerializedName("89")
                    public int value89;
                    @SerializedName("9")
                    public int value9;
                    @SerializedName("90")
                    public int value90;
                    @SerializedName("91")
                    public int value91;
                    @SerializedName("92")
                    public int value92;
                    @SerializedName("93")
                    public int value93;
                    @SerializedName("94")
                    public int value94;
                }
            }

            public static class ApiMstUseitem extends IdentifiablePOJO {
                public int api_category;
                public int api_id;
                public String api_name;
                public int api_price;
                public int api_usetype;
                public List<String> api_description;

                @Override
                public int getID() {
                    return api_id;
                }
            }
        }
    }
}
