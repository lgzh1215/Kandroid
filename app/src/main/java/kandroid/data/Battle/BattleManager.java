package kandroid.data.Battle;

import kandroid.data.ResponseDataListner;
import kandroid.observer.RawData;
import kandroid.utils.SparseIntArray;

public class BattleManager implements ResponseDataListner {

    public CompassData Compass;

    public BattleDay BattleDay;

    public BattleNight BattleNight;

    public BattleResultData Result;

    public BattleModes BattleMode;

    public int DroppedShipCount;

    public int DroppedEquipmentCount;

    public SparseIntArray DroppedItemCount;

    public String EnemyAdmiralName;

    public String EnemyAdmiralRank;

    public BattleManager() {
        DroppedItemCount = new SparseIntArray();
    }

    public enum BattleModes {
        Undefined(1),           //未定義
        Normal(2),              //昼夜戦(通常戦闘)
        Night(4),               //夜戦
        NightDay(8),            //夜昼戦
        AirBattle(16),           //航空戦
        AirRaid(32),             //長距離空襲戦
        Practice(64),            //演習
        BattlePhaseMask(0xFFFF),     //戦闘形態マスク
        CombinedTaskForce(0x10000),   //機動部隊
        CombinedSurface(0x20000),     //水上部隊
        CombinedMask(0x7FFF0000);        //連合艦隊仕様

        public final int flags;

        BattleModes(int flags) {
            this.flags = flags;
        }
    }

    @Override
    public void loadFromResponse(String apiName, RawData rawData) {
        switch (apiName) {
            case "api_req_map/start":
            case "api_req_map/next":
                BattleDay = null;
                BattleNight = null;
                Result = null;
                BattleMode = BattleModes.Undefined;
                Compass = new CompassData();
                Compass.loadFromResponse(apiName, rawData);
        }
    }
}