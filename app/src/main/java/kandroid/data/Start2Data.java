package kandroid.data;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kandroid.observer.kcsapi.api_start2.ApiStart2.ApiData.ApiMstMapinfo;
import kandroid.observer.kcsapi.api_start2.ApiStart2.ApiData.ApiMstMission;
import kandroid.observer.kcsapi.api_start2.ApiStart2.ApiData.ApiMstShip;
import kandroid.observer.kcsapi.api_start2.ApiStart2.ApiData.ApiMstSlotitem;
import kandroid.observer.kcsapi.api_start2.ApiStart2.ApiData.ApiMstSlotitemEquiptype;
import kandroid.observer.kcsapi.api_start2.ApiStart2.ApiData.ApiMstStype;
import kandroid.observer.kcsapi.api_start2.ApiStart2.ApiData.ApiMstUseitem;
import kandroid.utils.IDDictionary;
import kandroid.utils.Identifiable;

public class Start2Data {

    public IDDictionary<MasterShipData> masterShipData;
    public IDDictionary<EquipmentType> equipmentType;
    public IDDictionary<ShipType> shipType;
    public IDDictionary<MasterEquipmentData> masterEquipmentData;
    public IDDictionary<MasterUseItemData> masterUseItemData;
    public IDDictionary<MasterMapInfoData> masterMapInfoData;
    public IDDictionary<MissionData> missionData;

    public Start2Data() {
        masterShipData = new IDDictionary<>();
        equipmentType = new IDDictionary<>();
        shipType = new IDDictionary<>();
        masterEquipmentData = new IDDictionary<>();
        masterUseItemData = new IDDictionary<>();
        masterMapInfoData = new IDDictionary<>();
        missionData = new IDDictionary<>();
    }

    public static class MasterShipData extends Data<ApiMstShip> implements Identifiable {
        public MasterShipData() {
        }

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

    public static class EquipmentType extends Data<ApiMstSlotitemEquiptype> implements Identifiable {

        public int getTypeID() {
            return data.api_id;
        }

        public String getName() {
            return data.api_name;
        }

        @Override
        public int getID() {
            return data.api_id;
        }
    }

    public static class ShipType extends Data<ApiMstStype> implements Identifiable {

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

        @Override
        public int getID() {
            return data.api_id;
        }
    }

    public static class MasterEquipmentData extends Data<ApiMstSlotitem> implements Identifiable {

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

        @Override
        public int getID() {
            return data.api_id;
        }
    }

    public static class MasterUseItemData extends Data<ApiMstUseitem> implements Identifiable {

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

        @Override
        public int getID() {
            return data.api_id;
        }
    }

    public static class MasterMapInfoData extends Data<ApiMstMapinfo> implements Identifiable {

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

        @Override
        public int getID() {
            return data.api_id;
        }
    }

    public static class MissionData extends Data<ApiMstMission> implements Identifiable {

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

        @Override
        public int getID() {
            return data.api_id;
        }
    }
}
