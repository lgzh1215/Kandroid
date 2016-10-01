package kandroid.data;

import java.util.Date;

import kandroid.observer.POJO;
import kandroid.utils.Identifiable;

public class ArsenalData extends Data<ArsenalData.ApiKdock> implements Identifiable {

    public int getArsenalID() {
        return data.api_id;
    }

    public int getState() {
        return data.api_state;
    }

    public int getShipID() {
        return data.api_created_ship_id;
    }

    public Date getCompletionTime() {
        return new Date(data.api_complete_time);
    }

    public int getFuel() {
        return data.api_item1;
    }

    public int getAmmo() {
        return data.api_item2;
    }

    public int getSteel() {
        return data.api_item3;
    }

    public int getBauxite() {
        return data.api_item4;
    }

    public int getDevelopmentMaterial() {
        return data.api_item5;
    }

    @Override
    public int getID() {
        return data.api_id;
    }

    public static class ApiKdock extends POJO {
        public int api_id;
        public int api_state;
        public int api_created_ship_id;
        public int api_complete_time;
        public String api_complete_time_str;
        public int api_item1;
        public int api_item2;
        public int api_item3;
        public int api_item4;
        public int api_item5;
    }
}
