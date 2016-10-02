package kandroid.data;

import com.google.gson.annotations.*;
import java.lang.reflect.*;
import java.util.*;
import kandroid.observer.*;
import kandroid.utils.*;

public class Start2Data {

    public IDDictionary<MasterShipData> masterShipData;
    public IDDictionary<EquipmentType> equipmentType;
    public IDDictionary<ShipType> shipType;
    public IDDictionary<MasterEquipmentData> masterEquipmentData;
    public IDDictionary<MasterUseItemData> masterUseItemData;
    public IDDictionary<MasterMapInfoData> masterMapInfoData;
    public IDDictionary<MasterMissionData> masterMissionData;

    public Start2Data() {
        masterShipData = new IDDictionary<>();
        equipmentType = new IDDictionary<>();
        shipType = new IDDictionary<>();
        masterEquipmentData = new IDDictionary<>();
        masterUseItemData = new IDDictionary<>();
        masterMapInfoData = new IDDictionary<>();
        masterMissionData = new IDDictionary<>();
    }

<<<<<<< HEAD
<<<<<<< HEAD
    public static class MasterShipData extends Data<MasterShipData.ApiMstShip> implements Identifiable {
=======
    public static class MasterShipData extends Data<Start2Data.MasterShipData.ApiMstShip> implements Identifiable {
>>>>>>> origin/develop
=======
    public static class MasterShipData extends Data<Start2Data.MasterShipData.ApiMstShip> implements Identifiable {
>>>>>>> origin/develop
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
		
		public static class ApiMstShip extends POJO{
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
		}
    }

<<<<<<< HEAD
<<<<<<< HEAD
    public static class EquipmentType extends Data<EquipmentType.ApiMstSlotitemEquiptype> implements Identifiable {
=======
    public static class EquipmentType extends Data<Start2Data.EquipmentType.ApiMstSlotitemEquiptype> implements Identifiable {
>>>>>>> origin/develop
=======
    public static class EquipmentType extends Data<Start2Data.EquipmentType.ApiMstSlotitemEquiptype> implements Identifiable {
>>>>>>> origin/develop

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
		
		public static class ApiMstSlotitemEquiptype extends POJO{
			public int api_id;
			public String api_name;
			public int api_show_flg;
		}
    }

<<<<<<< HEAD
<<<<<<< HEAD
    public static class ShipType extends Data<ShipType.ApiMstStype> implements Identifiable {
=======
    public static class ShipType extends Data<Start2Data.ShipType.ApiMstStype> implements Identifiable {
>>>>>>> origin/develop
=======
    public static class ShipType extends Data<Start2Data.ShipType.ApiMstStype> implements Identifiable {
>>>>>>> origin/develop

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
		
		public static class ApiMstStype extends POJO {
			public ApiEquipType api_equip_type;
			public int api_id;
			public int api_kcnt;
			public String api_name;
			public int api_scnt;
			public int api_sortno;

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
    }

<<<<<<< HEAD
<<<<<<< HEAD
    public static class MasterEquipmentData extends Data<MasterEquipmentData.ApiMstSlotitem> implements Identifiable {
=======
    public static class MasterEquipmentData extends Data<Start2Data.MasterEquipmentData.ApiMstSlotitem> implements Identifiable {
>>>>>>> origin/develop
=======
    public static class MasterEquipmentData extends Data<Start2Data.MasterEquipmentData.ApiMstSlotitem> implements Identifiable {
>>>>>>> origin/develop

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
		
		public static class ApiMstSlotitem extends POJO {
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
		}
    }

<<<<<<< HEAD
<<<<<<< HEAD
    public static class MasterUseItemData extends Data<MasterUseItemData.ApiMstUseitem> implements Identifiable {
=======
    public static class MasterUseItemData extends Data<Start2Data.MasterUseItemData.ApiMstUseitem> implements Identifiable {
>>>>>>> origin/develop
=======
    public static class MasterUseItemData extends Data<Start2Data.MasterUseItemData.ApiMstUseitem> implements Identifiable {
>>>>>>> origin/develop

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
		
		public static class ApiMstUseitem extends POJO {
			public int api_category;
			public int api_id;
			public String api_name;
			public int api_price;
			public int api_usetype;
			public List<String> api_description;
		}
    }

<<<<<<< HEAD
<<<<<<< HEAD
    public static class MasterMapInfoData extends Data<MasterMapInfoData.ApiMstMapinfo> implements Identifiable {
=======
    public static class MasterMapInfoData extends Data<Start2Data.MasterMapInfoData.ApiMstMapinfo> implements Identifiable {
>>>>>>> origin/develop
=======
    public static class MasterMapInfoData extends Data<Start2Data.MasterMapInfoData.ApiMstMapinfo> implements Identifiable {
>>>>>>> origin/develop

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
		
		public static class ApiMstMapinfo extends POJO {
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
		}
    }

<<<<<<< HEAD
<<<<<<< HEAD
    public static class MasterMissionData extends Data<MasterMissionData.ApiMstMission> implements Identifiable {
=======
    public static class MissionData extends Data<Start2Data.MissionData.ApiMstMission> implements Identifiable {
>>>>>>> origin/develop
=======
    public static class MissionData extends Data<Start2Data.MissionData.ApiMstMission> implements Identifiable {
>>>>>>> origin/develop

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
		
		public static class ApiMstMission extends POJO {
			public String api_details;
			public int api_difficulty;
			public int api_id;
			public int api_maparea_id;
			public String api_name;
			public int api_return_flag;
			public int api_time;
			public double api_use_bull;
			public double api_use_fuel;
			public List<Integer> api_win_item1;
			public List<Integer> api_win_item2;
		}
    }
}
