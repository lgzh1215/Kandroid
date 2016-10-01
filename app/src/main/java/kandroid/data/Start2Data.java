package kandroid.data;

import com.google.gson.annotations.SerializedName;
import kandroid.observer.kcsapi.api_start2;
import kandroid.observer.kcsapi.api_start2.ApiStart2.ApiData.*;
import kandroid.observer.kcsapi.api_start2.IdentifiablePOJO;
import kandroid.utils.IDDictionary;
import kandroid.utils.Identifiable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Start2Data {

    public IDDictionary<MasterShipData> masterShipDatas;

    public Map<Integer, MasterShipData> masterShipData;
    public Map<Integer, EquipmentType> equipmentType;
    public Map<Integer, ShipType> shipType;
    public Map<Integer, MasterEquipmentData> masterEquipmentData;
    public Map<Integer, MasterUseItemData> masterUseItemData;
    public Map<Integer, MapInfoData> mapInfoData;
    public Map<Integer, MissionData> missionData;

    public Start2Data() {
        masterShipData = new HashMap<>();
        equipmentType = new HashMap<>();
        shipType = new HashMap<>();
        masterEquipmentData = new HashMap<>();
        masterUseItemData = new HashMap<>();
        mapInfoData = new HashMap<>();
        missionData = new HashMap<>();
    }

    public void setApiStart2(api_start2.ApiStart2 apiStart2) {

        // api_mst_ship
        putListToMap(apiStart2.api_data.api_mst_ship, masterShipData, MasterShipData.class);

        // api_mst_slotitem_equiptype
        putListToMap(apiStart2.api_data.api_mst_slotitem_equiptype, equipmentType, EquipmentType.class);

        // api_mst_stype 特別置換処理 mdzz
        apiStart2.api_data.api_mst_stype.get(7).api_name = "巡洋戦艦";
        putListToMap(apiStart2.api_data.api_mst_stype, shipType, ShipType.class);

        // api_mst_slotitem
        putListToMap(apiStart2.api_data.api_mst_slotitem, masterEquipmentData, MasterEquipmentData.class);

        // api_mst_useitem
        putListToMap(apiStart2.api_data.api_mst_useitem, masterUseItemData, MasterUseItemData.class);

        // api_mst_mapinfo
        putListToMap(apiStart2.api_data.api_mst_mapinfo, mapInfoData, MapInfoData.class);

        // api_mst_mission
        putListToMap(apiStart2.api_data.api_mst_mission, missionData, MissionData.class);

        // TODO api_mst_shipupgrade
//        for (api_start2.ApiStart2.ApiData.ApiMstShipupgrade apiMstShipupgrade : apiStart2.api_data.api_mst_shipupgrade) {
//            int idbefore = apiMstShipupgrade.api_current_ship_id;
//            int idafter = apiMstShipupgrade.api_id;
//            MasterShipData shipbefore = masterShipData.get(idbefore);
//            MasterShipData shipafter = masterShipData.get(idafter);
//            int level = apiMstShipupgrade.api_upgrade_level;
//        }
    }

	private static <
	E extends IdentifiablePOJO,
	L extends List<E>,
	V extends POJOData<E>,
	M extends Map<Integer, V>> void putListToMap(L list, M map, Class<V> clazz) {
        for (E data : list) {
            int id = data.getID();
            if (map.containsKey(id)) {
                map.get(id).setData(data);
            } else {
                V value = null;
                try {
                    value = clazz.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                assert value != null;
                value.setData(data);
                map.put(id, value);
            }
        }
    }

    public class MasterShipData extends POJOData<ApiMstShip> implements Identifiable {

        public int getShipID() {
			return data.api_id;
        }

        public int getAlbumNo() {
            return data.api_sortno;
        }

        public String getName() {
            return data.api_name;
        }

        public String getNameReading() {
            return data.api_yomi;
        }

        public int getShipType() {
            return data.api_stype;
        }

        public int getRemodelAfterLevel() {
            return data.api_afterlv;
        }

        public int getRemodelAfterShipID() {
            return Integer.parseInt(data.api_aftershipid);
        }

        public MasterShipData getRemodelAfterShip() {
            int remodelAfterShipID = getRemodelAfterShipID();
            return remodelAfterShipID > 0 ? KCDatabase.getInstance().master.masterShipData.get(remodelAfterShipID) : null;
        }

        public int getRemodelBeforeShipID() {
            throw new UnsupportedOperationException();
        }

        public MasterShipData getRemodelBeforeShip() {
            int remodelBeforeShipID = getRemodelBeforeShipID();
            return remodelBeforeShipID > 0 ? KCDatabase.getInstance().master.masterShipData.get(remodelBeforeShipID) : null;
        }

        public int getRemodelAmmo() {
            return data.api_afterbull;
        }

        public int getRemodelSteel() {
            return data.api_afterfuel;
        }

        public int getNeedBlueprint() {
            throw new UnsupportedOperationException();
        }

        public int getNeedCatapult() {
            throw new UnsupportedOperationException();
        }

        public int getHPMin() {
            return data.api_taik.get(0);
        }

        public int getHPMax() {
            return data.api_taik.get(1);
        }

        public int getArmorMin() {
            return data.api_souk.get(0);
        }

        public int getArmorMax() {
            return data.api_souk.get(1);
        }

        public int getFirepowerMin() {
            return data.api_houg.get(0);
        }

        public int getFirepowerMax() {
            return data.api_houg.get(1);
        }

        public int getTorpedoMin() {
            return data.api_raig.get(0);
        }

        public int getTorpedoMax() {
            return data.api_raig.get(1);
        }

        public int getAAMin() {
            return data.api_tyku.get(0);
        }

        public int getAAMax() {
            return data.api_tyku.get(1);
        }

        public int getASW() {
            throw new UnsupportedOperationException();
        }

        public int getEvasion() {
            throw new UnsupportedOperationException();
        }

        public int getLOS() {
            throw new UnsupportedOperationException();
        }

        public int getLuckMin() {
            return data.api_luck.get(0);
        }

        public int getLuckMax() {
            return data.api_luck.get(1);
        }

        public int getSpeed() {
            return data.api_soku;
        }

        public int getRange() {
            return data.api_leng;
        }

        public int getSlotSize() {
            return data.api_slot_num;
        }

        public List<Integer> getAircraft() {
            return data.api_maxeq;
        }

        public int getAircraftTotal() {
            int total = 0;
            for (Integer integer : getAircraft()) {
                total += integer;
            }
            return total;
        }

        public List<Integer> getDefaultSlot() {
            throw new UnsupportedOperationException();
        }

        public int getBuildingTime() {
            return data.api_buildtime;
        }

        public List<Integer> getMaterial() {
            return data.api_broken;
        }

        public List<Integer> getPowerUp() {
            return data.api_powup;
        }

        public int getRarity() {
            return data.api_backs;
        }

        public String getMessageGet() {
            return data.api_getmes;
        }

        public int getFuel() {
            return data.api_fuel_max;
        }

        public int getAmmo() {
            return data.api_bull_max;
        }

        public int getVoiceFlag() {
            return data.api_voicef;
        }

        @Override
        public int getID() {
            return data.api_id;
        }
    }

    public class EquipmentType extends POJOData<ApiMstSlotitemEquiptype> {

        public int getTypeID() {
            return data.api_id;
        }

        public String getName() {
            return data.api_name;
        }
    }

    public class ShipType extends POJOData<ApiMstStype> {

        private Map<Integer, Integer> api_equip_type;

        public int getTypeID() {
            return data.api_id;
        }

        public int getSortID() {
            return data.api_sortno;
        }

        public String getName() {
            return data.api_name;
        }

        public int getRepairTime() {
            return data.api_scnt;
        }

        // api_equip_type 特別置換処理 mdzz
        public Map<Integer, Integer> getEquipmentType() {
            if (api_equip_type == null) {
                api_equip_type = new HashMap<>();
                Field[] fields = data.api_equip_type.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    int name = Integer.parseInt(field.getAnnotation(SerializedName.class).value());
                    int value = 0;
                    try {
                        value = (int) field.get(data.api_equip_type);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    api_equip_type.put(name, value);
                }
            }
            return api_equip_type;
        }

        public void setApi_equip_type(Map<Integer, Integer> api_equip_type) {
            this.api_equip_type = api_equip_type;
        }
    }

    public class MasterEquipmentData extends POJOData<ApiMstSlotitem> {

        public int getEquipmentID() {
            return data.api_id;
        }

        public int getAlbumNo() {
            return data.api_sortno;
        }

        public String getName() {
            return data.api_name;
        }

        public List<Integer> getEquipmentType() {
            return data.api_type;
        }

        public int getArmor() {
            return data.api_souk;
        }

        public int getFirepower() {
            return data.api_houg;
        }

        public int getTorpedo() {
            return data.api_raig;
        }

        public int getBomber() {
            return data.api_baku;
        }

        public int getAA() {
            return data.api_tyku;
        }

        public int getASW() {
            return data.api_tais;
        }

        public int getAccuracy() {
            return data.api_houm;
        }

        public int getEvasion() {
            return data.api_houk;
        }

        public int getLOS() {
            return data.api_saku;
        }

        public int getLuck() {
            return data.api_luck;
        }

        public int getRange() {
            return data.api_leng;
        }

        public int getRarity() {
            return data.api_rare;
        }

        public List<Integer> getMaterial() {
            return data.api_broken;
        }

        public String getMessage() {
            return data.api_info.replace("<br>", "\n");
        }

        public int getAircraftCost() {
            return data.api_cost;
        }

        public int getAircraftDistance() {
            return data.api_distance;
        }
    }

    public class MasterUseItemData extends POJOData<ApiMstUseitem> {

        public int getItemID() {
            return data.api_id;
        }

        public int getUseType() {
            return data.api_usetype;
        }

        public int getCategory() {
            return data.api_category;
        }

        public String getName() {
            return data.api_name;
        }

        public String getDescription() {
            return data.api_description.get(0);
        }
    }

    public class MapInfoData extends POJOData<ApiMstMapinfo> {

        public int getMapID() {
            return data.api_id;
        }

        public int getMapAreaID() {
            return data.api_maparea_id;
        }

        public int getMapInfoID() {
            return data.api_no;
        }

        public String getName() {
            return data.api_name;
        }

        public int getDifficulty() {
            return data.api_level;
        }

        public String getOperationName() {
            return data.api_opetext;
        }

        public String getInformation() {
            return data.api_infotext.replace("<br>", "");
        }

        public int getRequiredDefeatedCount() {
            return data.api_required_defeat_count;
        }
    }

    public class MissionData extends POJOData<ApiMstMission> {

        public int getMissionID() {
            return data.api_id;
        }

        public int getMapAreaID() {
            return data.api_maparea_id;
        }

        public String getName() {
            return data.api_name;
        }

        public String getDetail() {
            return data.api_details;
        }

        public int getTime() {
            return data.api_time;
        }

        public int getDifficulty() {
            return data.api_difficulty;
        }

        public double getFuel() {
            return data.api_use_fuel;
        }

        public double getAmmo() {
            return data.api_use_bull;
        }

        public boolean getCancelable() {
            return data.api_return_flag != 0;
		}
    }
}
