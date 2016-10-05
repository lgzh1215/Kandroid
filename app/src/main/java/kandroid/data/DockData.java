package kandroid.data;

import java.util.Date;

import kandroid.observer.POJO;
import kandroid.utils.Identifiable;

public class DockData extends Data<DockData.ApiNdock> implements Identifiable {

    public int getDockID() {
        return data.api_id;
    }

    public int getState() {
        return data.api_state;
    }

    public int getShipID() {
        return data.api_ship_id;
    }

    public Date getCompletionTime() {
        return new Date(data.api_complete_time);
    }

    @Override
    public void setData(ApiNdock data) {
        if (this.data != null && getState() == 1 && data.api_state == 0 && getShipID() != 0) {
            KCDatabase.getShips().get(getID()).repair();
        }
        super.setData(data);
    }

    @Override
    public int getID() {
        return data.api_id;
    }

    public static class ApiNdock extends POJO {
        public int api_member_id;
        public int api_id;
        public int api_state;
        public int api_ship_id;
        public int api_complete_time;
        public String api_complete_time_str;
        public int api_item1;
        public int api_item2;
        public int api_item3;
        public int api_item4;
    }
}