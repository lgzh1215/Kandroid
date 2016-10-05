package kandroid.data.Battle;

import java.util.List;

import kandroid.data.Data;
import kandroid.data.KCDatabase;
import kandroid.data.MapInfoData;
import kandroid.data.ResponseDataListner;
import kandroid.observer.POJO;
import kandroid.observer.RawData;

public class CompassData extends Data<CompassData.ApiCompass> implements ResponseDataListner {

    public int getMapAreaID() {
        return data.api_maparea_id;
    }

    public int getMapInfoID() {
        return data.api_mapinfo_no;
    }

    public int getDestination() {
        return data.api_no;
    }

    public int getColorID() {
        return data.api_color_no;
    }

    public int getEventID() {
        return data.api_event_id;
    }

    public int getEventKind() {
        return data.api_event_kind;
    }

    public int getNextBranchCount() {
        return data.api_next;
    }

    public boolean isEndPoint() {
        return getNextBranchCount() == 0;
    }

    public int getCommentID() {
        return data.api_comment_kind;
    }

    public int getLaunchedRecon() {
        return data.api_production_kind;
    }

    //region Description
    public class GetItemData {
        public int ItemID;
        public int Metadata;
        public int Amount;

        public GetItemData(int itemID, int metadata, int amount) {
            ItemID = itemID;
            Metadata = metadata;
            Amount = amount;
        }
    }

    @Deprecated
    public GetItemData[] GetItems() {
        throw new UnsupportedOperationException();
    }
    //endregion

    public int getWhirlpoolItemID() {
        return data.api_happening == null ? -1 : data.api_happening.api_mst_id;
    }

    public int getWhirlpoolItemAmount() {
        return data.api_happening == null ? 0 : data.api_happening.api_count;
    }

    public boolean isWhirlpoolRadarFlag() {
        return data.api_happening != null && data.api_happening.api_dentan != 0;
    }

    public List<Integer> getRouteChoices() {
        return data.api_select_route == null ? null : data.api_select_route.api_select_cells;
    }

    public int getAirReconnaissancePlane() {
        return data.api_airsearch == null ? 0 : data.api_airsearch.api_plane_type;
    }

    public int getAirReconnaissanceResult() {
        return data.api_airsearch == null ? 0 : data.api_airsearch.api_result;
    }

    public boolean isHasAirRaid() {
        return data.api_destruction_battle != null;
    }

    public int getAirRaidDamageKind() {
        if (isHasAirRaid())
            return data.api_destruction_battle.api_lost_kind;
        else return 0;
    }

    public MapInfoData getMapInfo() {
        return KCDatabase.getMapInfoDatas().get(getMapAreaID() * 10 + getMapInfoID());
    }

    @Override
    public void loadFromResponse(String apiName, RawData rawData) {
        // TODO
    }

    public static class ApiCompass extends POJO {
        public int api_rashin_flg;
        public int api_rashin_id;
        public int api_maparea_id;
        public int api_mapinfo_no;
        public int api_no;
        public int api_color_no;
        public int api_event_id;
        public int api_event_kind;
        public int api_next;
        public int api_bosscell_no;
        public int api_bosscomp;
        public int api_comment_kind;
        public int api_production_kind;
        public ApiAirsearch api_airsearch;
        public ApiHappening api_happening;
        public ApiSelectRoute api_select_route;
        public ApiDestructionBattle api_destruction_battle;

        public static class ApiAirsearch {
            public int api_plane_type;
            public int api_result;
        }

        public static class ApiHappening {
            public int api_mst_id;
            public int api_count;
            public int api_dentan;
        }

        public static class ApiSelectRoute {
            public List<Integer> api_select_cells;
        }

        public static class ApiDestructionBattle {

            public int api_lost_kind;
        }
    }
}
