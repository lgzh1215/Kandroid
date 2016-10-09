package kandroid.data;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import kandroid.config.Config;
import kandroid.utils.IDDictionary;
import kandroid.utils.Identifiable;
import kandroid.utils.SparseArray;
import kandroid.utils.Utils;

public class KCDatabase {

    private static class MasterHolder {
        public static Start2Data master = loadMaster();

        private static Start2Data loadMaster() {
            System.out.println("loadMaster");
            Start2Data start2Data = null;
            try {
                File file = Config.Companion.getConfig().getSaveDataFile("master");
                String data = FileUtils.readFileToString(file, Charset.defaultCharset());
                start2Data = Utils.GSON.fromJson(data, Start2Data.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (start2Data == null)
                start2Data = new Start2Data();
            return start2Data;
        }
    }

    private static class PlayerListHolder {
        public static SparseArray<String> playerList = loadUserList();

        private static SparseArray<String> loadUserList() {
            System.out.println("loadList");
            SparseArray<String> list = new SparseArray<>();
            File[] files = Config.Companion.getConfig().getSaveUserFile(null).listFiles();
            if (files != null) {
                for (File file : files) {
                    String fileName = file.getName();
                    String[] split = fileName.split("@", 2);
                    if (split.length == 2)
                        list.put(Integer.parseInt(split[0]), split[1]);
                }
            }
            return list;
        }
    }

    private static int currentPlayerId;
    private static Player currentPlayer;
    private static final IDDictionary<Player> playerDatas = new IDDictionary<>();

    public static class Player implements Identifiable {

        public int id;
        public String name;
        public String apiToken;

        public IDDictionary<ShipData> ships = new IDDictionary<>();
        public IDDictionary<EquipmentData> equipments = new IDDictionary<>();
        public IDDictionary<UseItem> items = new IDDictionary<>();

        public IDDictionary<ArsenalData> arsenalData = new IDDictionary<>();
        public MaterialData material = new MaterialData();
        public AdmiralData admiral = new AdmiralData();
        public IDDictionary<DockData> dockData = new IDDictionary<>();

        public FleetManager fleets = new FleetManager();

        public IDDictionary<MapInfoData> mapInfoDatas = new IDDictionary<>();

        @Override
        public int getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Player player = (Player) o;

            return apiToken.equals(player.apiToken) || id != 0 && player.id != 0 && id == player.id;
        }
    }

    private static Player loadUserData(int id) {
        Player player = null;
        try {
            String name = getPlayerList().get(id);
            if (name != null) {
                File file = Config.Companion.getConfig().getSaveUserFile(String.valueOf(id) + '@' + name);
                String data = FileUtils.readFileToString(file, Charset.defaultCharset());
                player = Utils.GSON.fromJson(data, Player.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return player;
    }

    public static void saveMaster() {
        Start2Data master = getMaster();
        if (!master.isInited) return;
        try {
            File file = Config.Companion.getConfig().getSaveDataFile("master");
            String json = Utils.GSON.toJson(master);
            FileUtils.writeStringToFile(file, json, Charset.defaultCharset());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveUserData() {
        File file;
        String json;
        for (Player player : playerDatas) {
            try {
                file = Config.Companion.getConfig().getSaveDataFile(String.valueOf(player.id) + "@" + player.name);
                json = Utils.GSON.toJson(player);
                FileUtils.writeStringToFile(file, json, Charset.defaultCharset());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Start2Data getMaster() {
        return MasterHolder.master;
    }

    public static SparseArray<String> getPlayerList() {
        return PlayerListHolder.playerList;
    }

    public static Player getCurrentPlayer() {
        int id = currentPlayerId;
        Player player = playerDatas.get(id);
        if (player == null) {
            //从保存的数据中读取
            player = loadUserData(id);
            if (player == null) {
                //新用户 id=0
                player = new Player();
            }
        }
        return player;
    }

    public static IDDictionary<EquipmentData> getEquipments() {
        return getCurrentPlayer().equipments;
    }

    public static IDDictionary<ArsenalData> getArsenalData() {
        return getCurrentPlayer().arsenalData;
    }

    public static IDDictionary<UseItem> getUseItems() {
        return getCurrentPlayer().items;
    }

    public static MaterialData getMaterial() {
        return getCurrentPlayer().material;
    }

    public static AdmiralData getAdmiral() {
        return getCurrentPlayer().admiral;
    }

    public static IDDictionary<ShipData> getShips() {
        return getCurrentPlayer().ships;
    }

    public static IDDictionary<DockData> getDockData() {
        return getCurrentPlayer().dockData;
    }

    public static FleetManager getFleets() {
        return getCurrentPlayer().fleets;
    }

    public static IDDictionary<MapInfoData> getMapInfoDatas() {
        return getCurrentPlayer().mapInfoDatas;
    }
}