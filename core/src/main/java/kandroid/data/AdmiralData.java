package kandroid.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.Date;
import java.util.List;

import kandroid.observer.POJO;
import kandroid.utils.Identifiable;

public class AdmiralData extends Data<AdmiralData.ApiBasic> implements Identifiable {

    public AdmiralData() {
        data = new ApiBasic();
    }

    public String getAdmiralName() {
        return data.api_nickname;
    }

    public Date getStartTime() {
        return new Date(data.api_starttime);
    }

    public int getLevel() {
        return data.api_level;
    }

    public int getRank() {
        return data.api_rank;
    }

    public int getExp() {
        return data.api_experience;
    }

    public String getComment() {
        return data.api_comment;
    }

    public int getMaxShipCount() {
        return data.api_max_chara;
    }

    public int getMaxEquipmentCount() {
        return data.api_max_slotitem;
    }

    public int getFleetCount() {
        return data.api_count_deck;
    }

    public int getArsenalCount() {
        return data.api_count_kdock;
    }

    public int getDockCount() {
        return data.api_count_ndock;
    }

    public int getFurnitureCoin() {
        return data.api_fcoin;
    }

    public int getSortieWin() {
        return data.api_st_win;
    }

    public int getSortieLose() {
        return data.api_st_lose;
    }

    public int getMissionCount() {
        return data.api_ms_count;
    }

    public int getMissionSuccess() {
        return data.api_ms_success;
    }

    public int getPracticeWin() {
        return data.api_pt_win;
    }

    public int getPracticeLose() {
        return data.api_pt_lose;
    }

    public int getMaxResourceRegenerationAmount() {
        return getLevel() * 250 + 750;
    }

    public void loadFromResponse(String apiName, JsonElement data) {
        switch (apiName) {
            case "api_get_member/basic":
                ApiBasic apiBasic = new Gson().fromJson(data, ApiBasic.class);
                apiBasic.api_large_dock = this.data.api_large_dock;
                setData(apiBasic);
                break;
        }
    }

    @Override
    public int getId() {
        return data.api_member_id;
    }

    public static class ApiBasic extends POJO {
        public int api_member_id;
        public String api_nickname;
        public String api_nickname_id;
        public int api_active_flag;
        public long api_starttime;
        public int api_level;
        public int api_rank;
        public int api_experience;
        public Object api_fleetname;
        public String api_comment;
        public String api_comment_id;
        public int api_max_chara;
        public int api_max_slotitem;
        public int api_max_kagu;
        public int api_playtime;
        public int api_tutorial;
        public int api_count_deck;
        public int api_count_kdock;
        public int api_count_ndock;
        public int api_fcoin;
        public int api_st_win;
        public int api_st_lose;
        public int api_ms_count;
        public int api_ms_success;
        public int api_pt_win;
        public int api_pt_lose;
        public int api_pt_challenged;
        public int api_pt_challenged_win;
        public int api_firstflag;
        public int api_tutorial_progress;
        public int api_medals;
        public int api_large_dock;
        public List<Integer> api_furniture;
        public List<Integer> api_pvp;
    }
}
