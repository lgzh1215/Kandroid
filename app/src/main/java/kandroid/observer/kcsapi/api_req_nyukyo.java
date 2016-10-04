package kandroid.observer.kcsapi;

import java.util.Map;

import kandroid.data.DockData;
import kandroid.data.FleetData;
import kandroid.data.KCDatabase;
import kandroid.data.ShipData;
import kandroid.observer.ApiBase;
import kandroid.observer.RawData;

public class api_req_nyukyo {

    public static class start extends ApiBase {

        @Override
        protected void onDataReceived(RawData rawData) {
            Map<String, String> requestMap = rawData.getRequestMap();
            int shipId = Integer.valueOf(requestMap.get("api_ship_id"));
            int highSpeed = Integer.valueOf(requestMap.get("api_highspeed"));

            KCDatabase kcDatabase = KCDatabase.getInstance();

            ShipData ship = kcDatabase.ships.get(shipId);
            kcDatabase.material.fuel -= ship.getRepairFuel();
            kcDatabase.material.steel -= ship.getRepairSteel();
            if (highSpeed == 1) {
                ship.repair();
                kcDatabase.material.instantRepair--;
            } else if (ship.getRepairTime() < 60000) {
                ship.repair();
            }

            for (FleetData fleetData : kcDatabase.fleets.fleetDatas) {
                fleetData.loadFromRequest(getApiName(), rawData);
            }
        }

        @Override
        public String getApiName() {
            return "api_req_nyukyo/start";
        }
    }

    public static class speedchange extends ApiBase {

        @Override
        protected void onDataReceived(RawData rawData) {
            Map<String, String> requestMap = rawData.getRequestMap();
            Integer ndockId = Integer.valueOf(requestMap.get("api_ndock_id"));

            KCDatabase kcDatabase = KCDatabase.getInstance();
            DockData dockData = kcDatabase.dockData.get(ndockId);
            if (dockData.getState() == 1 && dockData.getShipID() != 0) {
                kcDatabase.ships.get(dockData.getShipID()).repair();
                dockData.getData().api_state = 0;
                dockData.getData().api_ship_id = 0;
            }

            kcDatabase.material.instantRepair--;

            for (FleetData fleetData : kcDatabase.fleets.fleetDatas) {
                fleetData.loadFromRequest(getApiName(), rawData);
            }
        }

        @Override
        public String getApiName() {
            return "api_req_nyukyo/speedchange";
        }
    }
}
