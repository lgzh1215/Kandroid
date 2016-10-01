package kandroid.data;

import java.util.HashMap;
import java.util.Map;

public class KCDatabase {
    
    public Start2Data master;

    public MaterialData material;
    public AdmiralData admiral;
    public Map<Integer, ShipData> shipData;
    public Map<Integer, DockData> dockData;

    private KCDatabase() {
        master = new Start2Data();

        material = new MaterialData();
        admiral = new AdmiralData();
        shipData = new HashMap<>();
        dockData = new HashMap<>();
    }
	
    public static KCDatabase getInstance() {
        return instance;
    }

    private static KCDatabase instance = new KCDatabase();
}
