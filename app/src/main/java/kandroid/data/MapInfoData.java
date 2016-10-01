package kandroid.data;

import kandroid.observer.POJO;
import kandroid.utils.Identifiable;

public class MapInfoData extends Data<MapInfoData.ApiMapInfo> implements Identifiable {

    public int getMapID() {
        return data.api_id;
    }

    public Start2Data.MasterMapInfoData getMasterMapInfo() {
        return KCDatabase.getInstance().master.masterMapInfoData.get(getMapID());
    }

    public int getMapAreaID() {
        return getMasterMapInfo().getMapAreaID();
    }

    public int getMapInfoID() {
        return getMasterMapInfo().getMapInfoID();
    }

    public String getName() {
        return getMasterMapInfo().getName();
    }

    public int getDifficulty() {
        return getMasterMapInfo().getDifficulty();
    }

    public String getOperationName() {
        return getMasterMapInfo().getOperationName();
    }

    public String getInformation() {
        return getMasterMapInfo().getInformation();
    }

    public int getRequiredDefeatedCount() {
        return getMasterMapInfo().getRequiredDefeatedCount();
    }

    public boolean isCleared() {
        return data.api_cleared != 0;
    }

    public int getCurrentDefeatedCount() {
        return data.api_defeat_count;
    }

    public int getMapHPCurrent() {
        return data.api_eventmap.api_now_maphp;
    }

    public int getMapHPMax() {
        return data.api_eventmap.api_max_maphp;
    }

    public int getEventDifficulty() {
        return data.api_eventmap.api_selected_rank;
    }

    public int getGaugeType() {
        return data.api_eventmap.api_gauge_type;
    }

    @Override
    public int getID() {
        return data.api_id;
    }

    public static class ApiMapInfo extends POJO {
        public int api_id;
        public int api_cleared;
        public int api_defeat_count;
        public int api_exboss_flag;
        public ApiEventmap api_eventmap = new ApiEventmap();

        private class ApiEventmap {
            public int api_now_maphp;
            public int api_max_maphp;
            public int api_state;
            public int api_selected_rank;
            public int api_gauge_type;
            public int api_airbase_enabled;
        }
    }
}
