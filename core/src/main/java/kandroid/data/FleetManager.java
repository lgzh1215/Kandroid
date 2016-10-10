package kandroid.data;

import kandroid.utils.IDDictionary;

public class FleetManager {

    public int combinedFlag;
    public IDDictionary<FleetData> fleetDatas;

    public FleetManager() {
        fleetDatas = new IDDictionary<>();
    }
}
