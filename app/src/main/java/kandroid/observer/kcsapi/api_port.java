package kandroid.observer.kcsapi;


import com.google.gson.Gson;

import java.util.List;

import kandroid.data.AdmiralData;
import kandroid.data.DockData;
import kandroid.data.FleetData;
import kandroid.data.KCDatabase;
import kandroid.data.MaterialData;
import kandroid.data.ShipData;
import kandroid.observer.ApiBase;
import kandroid.observer.RawData;
import kandroid.utils.IDDictionary;

public class api_port {

    public static class port extends ApiBase {

        public static final String API_NAME = "api_port/port";

        @Override
        public void onDataReceived(RawData rawData) {
            Port port = new Gson().fromJson(rawData.toString(), Port.class);
            KCDatabase kcDatabase = KCDatabase.getInstance();

            // api_material
            List<Port.ApiData.ApiMaterial> api_material = port.api_data.api_material;
            MaterialData materialData = kcDatabase.material;
            materialData.setFuel(api_material.get(0).api_value);
            materialData.setAmmo(api_material.get(1).api_value);
            materialData.setSteel(api_material.get(2).api_value);
            materialData.setBauxite(api_material.get(3).api_value);
            materialData.setInstantConstruction(api_material.get(4).api_value);
            materialData.setInstantRepair(api_material.get(5).api_value);
            materialData.setDevelopmentMaterial(api_material.get(6).api_value);
            materialData.setModdingMaterial(api_material.get(7).api_value);

            // api_basic
            kcDatabase.admiral.setData(port.api_data.api_basic);

            // api_ship
            IDDictionary<ShipData> shipData = kcDatabase.shipData;
            shipData.clear();
            for (ShipData.ApiShip apiShip : port.api_data.api_ship) {
                ShipData ship = new ShipData();
                ship.setData(apiShip);
                shipData.put(ship);
            }

            // api_ndock
            IDDictionary<DockData> dockData = kcDatabase.dockData;
            for (Port.ApiData.ApiNdock apiNdock : port.api_data.api_ndock) {
                int id = apiNdock.api_id;
                DockData dock = dockData.get(id);
                if (dock == null) {
                    dock = new DockData();
                    dock.setData(apiNdock);
                    dockData.put(dock);
                } else {
                    dock.setData(apiNdock);
                }
            }

            // api_deck_port
            IDDictionary<FleetData> fleetDatas = kcDatabase.fleets.fleetDatas;
            for (Port.ApiData.ApiDeckPort apiDeckPort : port.api_data.api_deck_port) {
                int id = apiDeckPort.api_id;
                FleetData fleetData = fleetDatas.get(id);
                if (fleetData == null) {
                    fleetData = new FleetData();
                    fleetData.setData(apiDeckPort);
                    fleetDatas.put(fleetData);
                } else {
                    fleetData.setData(apiDeckPort);
                }
            }

            kcDatabase.fleets.combinedFlag = port.api_data.api_combined_flag;

            //TODO
        }

        @Override
        public String getApiName() {
            return API_NAME;
        }

        public static class Port {

            public int api_result;
            public String api_result_msg;
            public ApiData api_data;

            public static class ApiData {
                public ApiBasic api_basic;
                public int api_combined_flag;
                public int api_p_bgm_id;
                public int api_parallel_quest_count;
                public List<ApiMaterial> api_material;
                public List<ApiDeckPort> api_deck_port;
                public List<ApiNdock> api_ndock;
                public List<ApiShip> api_ship;
                public List<ApiLog> api_log;

                public static class ApiBasic extends AdmiralData.ApiBasic {
                }

                public static class ApiMaterial {
                    public int api_member_id;
                    public int api_id;
                    public int api_value;
                }

                public static class ApiDeckPort extends FleetData.ApiDeck {
                }

                public static class ApiNdock extends DockData.ApiNdock {
                }

                public static class ApiShip extends ShipData.ApiShip {
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