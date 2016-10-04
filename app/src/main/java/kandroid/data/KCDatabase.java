package kandroid.data;

import kandroid.utils.IDDictionary;

public class KCDatabase {

    public transient Start2Data master;

    public IDDictionary<EquipmentData> equipments;
    public IDDictionary<ArsenalData> arsenalData;
    public IDDictionary<UseItem> useItems;

    public MaterialData material;
    public AdmiralData admiral;
    public IDDictionary<ShipData> ships;
    public IDDictionary<DockData> dockData;

    public FleetManager fleets;

    public IDDictionary<MapInfoData> mapInfoDatas;

    static {
        readData();
    }

    private static void readData() {

    }

    private static void saveData() {

    }

    private KCDatabase() {
        master = new Start2Data();

        equipments = new IDDictionary<>();
        arsenalData = new IDDictionary<>();
        useItems = new IDDictionary<>();

        material = new MaterialData();
        admiral = new AdmiralData();
        ships = new IDDictionary<>();
        dockData = new IDDictionary<>();

        fleets = new FleetManager();

        mapInfoDatas = new IDDictionary<>();
    }
	
    public static KCDatabase getInstance() {
        return instance;
    }

    private static KCDatabase instance = new KCDatabase();
}
