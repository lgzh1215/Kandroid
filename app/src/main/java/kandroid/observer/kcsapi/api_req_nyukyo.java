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


            ShipData ship = KCDatabase.getShips().get(shipId);
            KCDatabase.getMaterial().setFuel(KCDatabase.getMaterial().getFuel() - ship.getRepairFuel());
            KCDatabase.getMaterial().setSteel(KCDatabase.getMaterial().getSteel() - ship.getRepairSteel());
            if (highSpeed == 1) {
                ship.repair();
                KCDatabase.getMaterial().setInstantRepair(KCDatabase.getMaterial().getInstantRepair() - 1);
            } else if (ship.getRepairTime() < 60000) {
                ship.repair();
            }

            for (FleetData fleetData : KCDatabase.getFleets().fleetDatas) {
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

            DockData dockData = KCDatabase.getDockData().get(ndockId);
            if (dockData.getState() == 1 && dockData.getShipID() != 0) {
                KCDatabase.getShips().get(dockData.getShipID()).repair();
                dockData.getData().api_state = 0;
                dockData.getData().api_ship_id = 0;
            }

            KCDatabase.getMaterial().setInstantRepair(KCDatabase.getMaterial().getInstantRepair() - 1);

            for (FleetData fleetData : KCDatabase.getFleets().fleetDatas) {
                fleetData.loadFromRequest(getApiName(), rawData);
            }
        }

        @Override
        public String getApiName() {
            return "api_req_nyukyo/speedchange";
        }
    }
}
