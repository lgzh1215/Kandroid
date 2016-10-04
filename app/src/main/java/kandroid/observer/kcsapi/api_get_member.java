package kandroid.observer.kcsapi;

import com.google.gson.Gson;

import java.util.List;

import kandroid.data.AdmiralData;
import kandroid.data.ArsenalData;
import kandroid.data.DockData;
import kandroid.data.EquipmentData;
import kandroid.data.FleetData;
import kandroid.data.KCDatabase;
import kandroid.data.MapInfoData;
import kandroid.data.MaterialData;
import kandroid.data.ShipData;
import kandroid.data.UseItem;
import kandroid.observer.ApiBase;
import kandroid.observer.POJO;
import kandroid.observer.RawData;
import kandroid.utils.IDDictionary;

public class api_get_member {

    public static class require_info extends ApiBase {

        public static final String API_NAME = "api_get_member/require_info";

        @Override
        protected void onDataReceived(RawData rawData) {
            Require_info require_info = new Gson().fromJson(rawData.toString(), Require_info.class);

            // api_slot_item
            IDDictionary<EquipmentData> equipmentData = KCDatabase.getInstance().equipments;
            equipmentData.clear();
            for (Require_info.ApiData.ApiSlotItem apiSlotItem : require_info.api_data.api_slot_item) {
                EquipmentData equipment = new EquipmentData();
                equipment.setData(apiSlotItem);
                equipmentData.put(equipment);
            }

            // api_kdock
            IDDictionary<ArsenalData> arsenalData = KCDatabase.getInstance().arsenalData;
            for (Require_info.ApiData.ApiKdock apiKdock : require_info.api_data.api_kdock) {
                int id = apiKdock.api_id;
                ArsenalData kdock = arsenalData.get(id);
                if (kdock == null) {
                    kdock = new ArsenalData();
                    kdock.setData(apiKdock);
                    arsenalData.put(kdock);
                } else {
                    kdock.setData(apiKdock);
                }
            }

            // api_useitem
            IDDictionary<UseItem> useItems = KCDatabase.getInstance().useItems;
            useItems.clear();
            for (Require_info.ApiData.ApiUseitem apiUseitem : require_info.api_data.api_useitem) {
                UseItem useItem = new UseItem();
                useItem.setData(apiUseitem);
                useItems.put(useItem);
            }
        }

        @Override
        public String getApiName() {
            return API_NAME;
        }

        public static class Require_info extends POJO {

            public int api_result;
            public String api_result_msg;
            public ApiData api_data;

            public static class ApiData {
                public ApiBasic api_basic;
                public Object api_unsetslot;
                public List<ApiSlotItem> api_slot_item;
                public List<ApiKdock> api_kdock;
                public List<ApiUseitem> api_useitem;
                public List<ApiFurniture> api_furniture;

                public static class ApiBasic {
                    public int api_member_id;
                    public int api_firstflag;
                }

                public static class ApiSlotItem extends EquipmentData.ApiSlotItem {
                }

                public static class ApiKdock extends ArsenalData.ApiKdock {
                }

                public static class ApiUseitem extends UseItem.ApiUseitem {
                }

                public static class ApiFurniture {
                    public int api_id;
                    public int api_furniture_type;
                    public int api_furniture_no;
                    public int api_furniture_id;
                }
            }
        }
    }

    public static class basic extends ApiBase {

        public static final String API_NAME = "api_get_member/basic";

        @Override
        protected void onDataReceived(RawData rawData) {
            Basic basic = new Gson().fromJson(rawData.toString(), Basic.class);
            KCDatabase.getInstance().admiral.setData(basic.api_data);
        }

        @Override
        public String getApiName() {
            return API_NAME;
        }


        public static class Basic {
            public int api_result;
            public String api_result_msg;
            public ApiData api_data;

            public static class ApiData extends AdmiralData.ApiBasic {
            }
        }

    }

    public static class slot_item extends ApiBase {

        public static final String API_NAME = "api_get_member/slot_item";

        @Override
        protected void onDataReceived(RawData rawData) {
            Slot_item slot_item = new Gson().fromJson(rawData.toString(), Slot_item.class);

            IDDictionary<EquipmentData> equipmentData = KCDatabase.getInstance().equipments;
            equipmentData.clear();
            for (Slot_item.ApiData apiData : slot_item.api_data) {
                EquipmentData equipment = new EquipmentData();
                equipment.setData(apiData);
                equipmentData.put(equipment);
            }

            //TODO
        }

        @Override
        public String getApiName() {
            return API_NAME;
        }

        public static class Slot_item {

            public int api_result;
            public String api_result_msg;
            public List<ApiData> api_data;

            public static class ApiData extends EquipmentData.ApiSlotItem {
            }
        }
    }

    public static class kdock extends ApiBase {

        public static final String API_NAME = "api_get_member/kdock";

        @Override
        protected void onDataReceived(RawData rawData) {
            Kdock kdock = new Gson().fromJson(rawData.toString(), Kdock.class);

            IDDictionary<ArsenalData> arsenalData = KCDatabase.getInstance().arsenalData;
            for (Kdock.ApiData apiData : kdock.api_data) {
                int id = apiData.api_id;
                ArsenalData arsenal = arsenalData.get(id);
                if (arsenal == null) {
                    arsenal = new ArsenalData();
                    arsenal.setData(apiData);
                    arsenalData.put(arsenal);
                } else {
                    arsenal.setData(apiData);
                }
            }
        }

        @Override
        public String getApiName() {
            return API_NAME;
        }

        public static class Kdock {

            public int api_result;
            public String api_result_msg;
            public List<ApiData> api_data;

            public static class ApiData extends ArsenalData.ApiKdock {
            }
        }
    }

    public static class useitem extends ApiBase {

        public static final String API_NAME = "api_get_member/useitem";

        @Override
        protected void onDataReceived(RawData rawData) {
            UseItem useItem = new Gson().fromJson(rawData.toString(), UseItem.class);

            // api_useitem
            IDDictionary<kandroid.data.UseItem> useItems = KCDatabase.getInstance().useItems;
            useItems.clear();
            for (UseItem.ApiData apiData : useItem.api_data) {
                kandroid.data.UseItem item = new kandroid.data.UseItem();
                item.setData(apiData);
                useItems.put(item);
            }
        }

        @Override
        public String getApiName() {
            return API_NAME;
        }

        public static class UseItem {
            public int api_result;
            public String api_result_msg;
            public List<ApiData> api_data;

            public static class ApiData extends kandroid.data.UseItem.ApiUseitem {
            }
        }
    }

    public static class material extends ApiBase {

        public static final String API_NAME = "api_get_member/material";

        @Override
        protected void onDataReceived(RawData rawData) {
            Material material = new Gson().fromJson(rawData.toString(), Material.class);
            List<Material.ApiData> api_data = material.api_data;

            MaterialData materialData = KCDatabase.getInstance().material;
            materialData.setFuel(api_data.get(0).api_value);
            materialData.setAmmo(api_data.get(1).api_value);
            materialData.setSteel(api_data.get(2).api_value);
            materialData.setBauxite(api_data.get(3).api_value);
            materialData.setInstantConstruction(api_data.get(4).api_value);
            materialData.setInstantRepair(api_data.get(5).api_value);
            materialData.setDevelopmentMaterial(api_data.get(6).api_value);
            materialData.setModdingMaterial(api_data.get(7).api_value);
        }

        @Override
        public String getApiName() {
            return API_NAME;
        }

        public static class Material extends POJO {

            public int api_result;
            public String api_result_msg;
            public List<ApiData> api_data;

            public static class ApiData {
                public int api_member_id;
                public int api_id;
                public int api_value;
            }
        }
    }

    public static class ndock extends ApiBase {

        public static final String API_NAME = "api_get_member/ndock";

        @Override
        protected void onDataReceived(RawData rawData) {
            Ndock ndock = new Gson().fromJson(rawData.toString(), Ndock.class);

            IDDictionary<DockData> dockData = KCDatabase.getInstance().dockData;
            for (Ndock.ApiData apiData : ndock.api_data) {
                int id = apiData.api_id;
                DockData dock = dockData.get(id);
                if (dock == null) {
                    dock = new DockData();
                    dock.setData(apiData);
                    dockData.put(dock);
                } else {
                    dock.setData(apiData);
                }
            }

            for (FleetData fleetData : KCDatabase.getInstance().fleets.fleetDatas) {
                fleetData.loadFromResponse(getApiName(), rawData);
            }
        }

        @Override
        public String getApiName() {
            return API_NAME;
        }

        public static class Ndock {
            public int api_result;
            public String api_result_msg;
            public List<ApiData> api_data;

            public static class ApiData extends DockData.ApiNdock {
            }
        }
    }

    public static class deck extends ApiBase {

        public static final String API_NAME = "api_get_member/deck";

        @Override
        protected void onDataReceived(RawData rawData) {
            Deck deck = new Gson().fromJson(rawData.toString(), Deck.class);

            IDDictionary<FleetData> fleetDatas = KCDatabase.getInstance().fleets.fleetDatas;
            for (Deck.ApiData apiData : deck.api_data) {
                int id = apiData.api_id;
                FleetData fleetData = fleetDatas.get(id);
                if (fleetData == null) {
                    fleetData = new FleetData();
                    fleetData.setData(apiData);
                    fleetDatas.put(fleetData);
                } else {
                    fleetData.setData(apiData);
                }
            }
        }

        @Override
        public String getApiName() {
            return API_NAME;
        }

        public static class Deck {

            public int api_result;
            public String api_result_msg;
            public List<ApiData> api_data;

            public static class ApiData extends FleetData.ApiDeck {
            }
        }
    }

    public static class mapinfo extends ApiBase {

        public static final String API_NAME = "api_get_member/mapinfo";

        @Override
        protected void onDataReceived(RawData rawData) {
            MapInfo mapInfo = new Gson().fromJson(rawData.toString(), MapInfo.class);
            IDDictionary<MapInfoData> mapInfoDatas = KCDatabase.getInstance().mapInfoDatas;
            for (MapInfo.ApiData apiData : mapInfo.api_data) {
                int id = apiData.api_id;
                MapInfoData mapInfoData = mapInfoDatas.get(id);
                if (mapInfoData == null) {
                    mapInfoData = new MapInfoData();
                    mapInfoData.setData(apiData);
                    mapInfoDatas.put(mapInfoData);
                } else {
                    mapInfoData.setData(apiData);
                }
            }
        }

        @Override
        public String getApiName() {
            return API_NAME;
        }

        public static class MapInfo {
            public int api_result;
            public String api_result_msg;
            public List<ApiData> api_data;

            public static class ApiData extends MapInfoData.ApiMapInfo {
            }
        }
    }

    public static class ship2 extends ApiBase {
        public static final String API_NAME = "api_get_member/ship2";

        @Override
        protected void onDataReceived(RawData rawData) {
            Ship2 ship2 = new Gson().fromJson(rawData.toString(), Ship2.class);
            KCDatabase kcDatabase = KCDatabase.getInstance();

            // api_data
            IDDictionary<ShipData> shipData = kcDatabase.ships;
            shipData.clear();
            for (ShipData.ApiShip apiShip : ship2.api_data) {
                ShipData ship = new ShipData();
                ship.setData(apiShip);
                shipData.put(ship);
            }

            // api_data_deck
            IDDictionary<FleetData> fleetDatas = kcDatabase.fleets.fleetDatas;
            for (FleetData.ApiDeck apiDeck : ship2.api_data_deck) {
                int id = apiDeck.api_id;
                FleetData fleetData = fleetDatas.get(id);
                if (fleetData == null) {
                    fleetData = new FleetData();
                    fleetData.setData(apiDeck);
                    fleetDatas.put(fleetData);
                } else {
                    fleetData.setData(apiDeck);
                }
            }
        }

        @Override
        public String getApiName() {
            return API_NAME;
        }

        public static class Ship2 {
            public int api_result;
            public String api_result_msg;
            public List<ApiData> api_data;
            public List<ApiDataDeck> api_data_deck;

            public static class ApiData extends ShipData.ApiShip {
            }

            public static class ApiDataDeck extends FleetData.ApiDeck {
            }
        }
    }

    public static class ship3 extends ApiBase {

        public static final String API_NAME = "api_get_member/ship3";

        @Override
        protected void onDataReceived(RawData rawData) {
            Ship3 ship3 = new Gson().fromJson(rawData.toString(), Ship3.class);
            KCDatabase kcDatabase = KCDatabase.getInstance();

            // api_ship_data
            for (Ship3.ApiData.ApiShipData apiShipData : ship3.api_data.api_ship_data) {
                int id = apiShipData.api_id;

                ShipData ship = kcDatabase.ships.get(id);
                ship.setData(apiShipData);

                for (int equipmentId : ship.getSlot()) {
                    if (equipmentId == -1) continue;
                    if (!kcDatabase.equipments.containsKey(equipmentId)) {
                        EquipmentData newEquipment = new EquipmentData();
                        EquipmentData.ApiSlotItem apiSlotItem = new EquipmentData.ApiSlotItem();
                        apiSlotItem.api_id = equipmentId;
                        apiSlotItem.api_slotitem_id = 1;
                        newEquipment.setData(apiSlotItem);
                        kcDatabase.equipments.put(newEquipment);
                    }
                }
            }

            // api_deck_data
            IDDictionary<FleetData> fleetDatas = kcDatabase.fleets.fleetDatas;
            for (Ship3.ApiData.ApiDeckData apiDeckData : ship3.api_data.api_deck_data) {
                int id = apiDeckData.api_id;
                FleetData fleetData = fleetDatas.get(id);
                if (fleetData == null) {
                    fleetData = new FleetData();
                    fleetData.setData(apiDeckData);
                    fleetDatas.put(fleetData);
                } else {
                    fleetData.setData(apiDeckData);
                }
            }
        }

        @Override
        public String getApiName() {
            return API_NAME;
        }

        public static class Ship3 {
            public int api_result;
            public String api_result_msg;
            public ApiData api_data;

            public static class ApiData {
                public Object api_slot_data;
                public List<ApiShipData> api_ship_data;
                public List<ApiDeckData> api_deck_data;

                public static class ApiShipData extends ShipData.ApiShip {
                }

                public static class ApiDeckData extends FleetData.ApiDeck {
                }
            }
        }

    }

    public static class ship_deck extends ApiBase {

        public static final String API_NAME = "api_get_member/ship_deck";

        @Override
        protected void onDataReceived(RawData rawData) {
            ShipDeck shipDeck = new Gson().fromJson(rawData.toString(), ShipDeck.class);
            KCDatabase kcDatabase = KCDatabase.getInstance();

            //api_ship_data
            IDDictionary<ShipData> shipDatas = kcDatabase.ships;
            for (ShipData.ApiShip apiShip : shipDeck.api_data.api_ship_data) {
                int id = apiShip.api_id;
                ShipData dock = shipDatas.get(id);
                if (dock == null) {
                    dock = new ShipData();
                    dock.setData(apiShip);
                    shipDatas.put(dock);
                } else {
                    dock.setData(apiShip);
                }
            }

            //api_deck_data
            IDDictionary<FleetData> fleetDatas = kcDatabase.fleets.fleetDatas;
            for (FleetData.ApiDeck apiDeck : shipDeck.api_data.api_deck_data) {
                int id = apiDeck.api_id;
                FleetData fleetData = fleetDatas.get(id);
                if (fleetData == null) {
                    fleetData = new FleetData();
                    fleetData.setData(apiDeck);
                    fleetDatas.put(fleetData);
                } else {
                    fleetData.setData(apiDeck);
                }
            }
        }

        @Override
        public String getApiName() {
            return API_NAME;
        }

        public static class ShipDeck {
            public int api_result;
            public String api_result_msg;
            public ApiData api_data;

            public static class ApiData {
                public List<ApiShipData> api_ship_data;
                public List<ApiDeckData> api_deck_data;

                public static class ApiShipData extends ShipData.ApiShip {
                }

                public static class ApiDeckData extends FleetData.ApiDeck {
                }
            }
        }
    }
}