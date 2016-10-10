package kandroid.data;


import java.util.Date;
import java.util.List;

import kandroid.observer.POJO;
import kandroid.observer.RawData;
import kandroid.utils.Identifiable;

public class FleetData extends Data<FleetData.ApiDeck> implements Identifiable, RequestDataListner, ResponseDataListner {

    private boolean inSortie;

    public int getFleetID() {
        return data.api_id;
    }

    public String getName() {
        return data.api_name;
    }

    public int getExpeditionState() {
        return data.api_mission.get(0);
    }

    public int getExpeditionDestination() {
        return data.api_mission.get(1);
    }

    public Date getExpeditionTime() {
        return new Date(data.api_mission.get(2));
    }

    public List<Integer> getMembers() {
        return data.api_ship;
    }

    public List<ShipData> getMembersInstance() {
        throw new UnsupportedOperationException();
    }

    public List<ShipData> getMembersWithoutEscaped() {
        throw new UnsupportedOperationException();
    }

    public List<Integer> getEscapedShipList() {
        throw new UnsupportedOperationException();
    }

    public boolean isInSortie() {
        return inSortie;
    }

    public Date getConditionTime() {
        throw new UnsupportedOperationException();
    }

    public boolean isConditionTimeLocked() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getId() {
        return data.api_id;
    }

    @Override
    public void loadFromRequest(String apiName, RawData rawData) {
        switch (apiName) {
            case "api_req_nyukyo/start":
            case "api_req_nyukyo/speedchange":
                ShortenConditionTimer();
                break;
        }
    }

    private void ShortenConditionTimer() {
        //TODO
    }

    @Override
    public void loadFromResponse(String apiName, RawData rawData) {
        switch (apiName) {
            case "api_get_member/ndock":
                ShortenConditionTimer();
                break;
        }
    }

    //TODO

    public static class ApiDeck extends POJO {
        public int api_member_id;
        public int api_id;
        public String api_name;
        public String api_name_id;
        public String api_flagship;
        public List<Integer> api_mission;
        public List<Integer> api_ship;
    }
}