package kandroid.data;

import kandroid.utils.IDDictionary;

public class KCDatabase {
    
    public Start2Data master;

    public IDDictionary<EquipmentData> equipmentData;
    public IDDictionary<ArsenalData> arsenalData;
    public IDDictionary<UseItem> useItems;

    public MaterialData material;
    public AdmiralData admiral;
    public IDDictionary<ShipData> shipData;
    public IDDictionary<DockData> dockData;

    public FleetManager fleets;

    public IDDictionary<MapInfoData> mapInfoDatas;

    private KCDatabase() {
        master = new Start2Data();

        equipmentData = new IDDictionary<>();
        arsenalData = new IDDictionary<>();

        material = new MaterialData();
        admiral = new AdmiralData();
        shipData = new IDDictionary<>();
        dockData = new IDDictionary<>();

        fleets = new FleetManager();

        mapInfoDatas = new IDDictionary<>();
    }
	
    public static KCDatabase getInstance() {
        return instance;
    }

    private static KCDatabase instance = new KCDatabase();
}
