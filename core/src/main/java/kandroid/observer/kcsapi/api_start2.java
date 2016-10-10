package kandroid.observer.kcsapi;

import com.google.gson.Gson;

import java.util.List;

import kandroid.data.KCDatabase;
import kandroid.data.Start2Data;
import kandroid.observer.ApiBase;
import kandroid.observer.RawData;

public class api_start2 extends ApiBase {

    public static final String API_NAME = "api_start2";

    @Override
    protected void onDataReceived(RawData rawData) {
        ApiStart2 apiStart2 = new Gson().fromJson(rawData.toString(), ApiStart2.class);
        Start2Data start2Data = KCDatabase.INSTANCE.getMaster();

        // api_mst_ship
        for (ApiStart2.ApiData.ApiMstShip apiMstShip : apiStart2.api_data.api_mst_ship) {
            int id = apiMstShip.api_id;
            Start2Data.MasterShipData ship = start2Data.masterShipData.get(id);
            if (ship == null) {
                ship = new Start2Data.MasterShipData();
                ship.setData(apiMstShip);
                start2Data.masterShipData.put(ship);
            } else {
                ship.setData(apiMstShip);
            }
        }

        // api_mst_slotitem_equiptype
        for (ApiStart2.ApiData.ApiMstSlotitemEquiptype apiMstSlotitemEquiptype : apiStart2.api_data.api_mst_slotitem_equiptype) {
            int id = apiMstSlotitemEquiptype.api_id;
            Start2Data.EquipmentType type = start2Data.equipmentType.get(id);
            if (type == null) {
                type = new Start2Data.EquipmentType();
                type.setData(apiMstSlotitemEquiptype);
                start2Data.equipmentType.put(type);
            } else {
                type.setData(apiMstSlotitemEquiptype);
            }
        }

        // api_mst_stype 特別置換処理 mdzz
        apiStart2.api_data.api_mst_stype.get(7).api_name = "巡洋戦艦";
        for (ApiStart2.ApiData.ApiMstStype apiMstStype : apiStart2.api_data.api_mst_stype) {
            int id = apiMstStype.api_id;
            Start2Data.ShipType type = start2Data.shipType.get(id);
            if (type == null) {
                type = new Start2Data.ShipType();
                type.setData(apiMstStype);
                start2Data.shipType.put(type);
            } else {
                type.setData(apiMstStype);
            }
        }

        // api_mst_slotitem
        for (ApiStart2.ApiData.ApiMstSlotitem apiMstSlotitem : apiStart2.api_data.api_mst_slotitem) {
            int id = apiMstSlotitem.api_id;
            Start2Data.MasterEquipmentData equipment = start2Data.masterEquipmentData.get(id);
            if (equipment == null) {
                equipment = new Start2Data.MasterEquipmentData();
                equipment.setData(apiMstSlotitem);
                start2Data.masterEquipmentData.put(equipment);
            } else {
                equipment.setData(apiMstSlotitem);
            }
        }

        // api_mst_useitem
        for (ApiStart2.ApiData.ApiMstUseitem apiMstUseitem : apiStart2.api_data.api_mst_useitem) {
            int id = apiMstUseitem.api_id;
            Start2Data.MasterUseItemData useItem = start2Data.masterUseItemData.get(id);
            if (useItem == null) {
                useItem = new Start2Data.MasterUseItemData();
                useItem.setData(apiMstUseitem);
                start2Data.masterUseItemData.put(useItem);
            } else {
                useItem.setData(apiMstUseitem);
            }
        }

        // api_mst_mapinfo
        for (ApiStart2.ApiData.ApiMstMapinfo apiMstMapinfo : apiStart2.api_data.api_mst_mapinfo) {
            int id = apiMstMapinfo.api_id;
            Start2Data.MasterMapInfoData mapInfo = start2Data.masterMapInfoData.get(id);
            if (mapInfo == null) {
                mapInfo = new Start2Data.MasterMapInfoData();
                mapInfo.setData(apiMstMapinfo);
                start2Data.masterMapInfoData.put(mapInfo);
            } else {
                mapInfo.setData(apiMstMapinfo);
            }
        }

        // api_mst_mission
        for (ApiStart2.ApiData.ApiMstMission apiMstMission : apiStart2.api_data.api_mst_mission) {
            int id = apiMstMission.api_id;
            Start2Data.MasterMissionData mission = start2Data.masterMissionData.get(id);
            if (mission == null) {
                mission = new Start2Data.MasterMissionData();
                mission.setData(apiMstMission);
                start2Data.masterMissionData.put(mission);
            } else {
                mission.setData(apiMstMission);
            }
        }

        // TODO api_mst_shipupgrade
//        for (api_start2.ApiStart2.ApiData.ApiMstShipupgrade apiMstShipupgrade : apiStart2.api_data.api_mst_shipupgrade) {
//            int idbefore = apiMstShipupgrade.api_current_ship_id;
//            int idafter = apiMstShipupgrade.api_id;
//            MasterShipData shipbefore = masterShipData.getApi(idbefore);
//            MasterShipData shipafter = masterShipData.getApi(idafter);
//            int level = apiMstShipupgrade.api_upgrade_level;
//        }

        KCDatabase.INSTANCE.saveMaster();
    }

    @Override
    public String getApiName() {
        return API_NAME;
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

            public static class ApiMstMapinfo extends Start2Data.MasterMapInfoData.ApiMstMapinfo {
            }

            public static class ApiMstMission extends Start2Data.MasterMissionData.ApiMstMission {
            }

            public static class ApiMstPayitem {
                public String api_description;
                public int api_id;
                public String api_name;
                public int api_price;
                public int api_type;
                public List<Integer> api_item;
            }

            public static class ApiMstShip extends Start2Data.MasterShipData.ApiMstShip {
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

            public static class ApiMstSlotitem extends Start2Data.MasterEquipmentData.ApiMstSlotitem {
            }

            public static class ApiMstSlotitemEquiptype extends Start2Data.EquipmentType.ApiMstSlotitemEquiptype {
            }

            public static class ApiMstStype extends Start2Data.ShipType.ApiMstStype {
            }

            public static class ApiMstUseitem extends Start2Data.MasterUseItemData.ApiMstUseitem {
            }
        }
    }
}
