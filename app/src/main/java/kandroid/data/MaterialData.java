package kandroid.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import kandroid.observer.ApiBase;
import kandroid.observer.kcsapi.api_port;

import java.util.List;

public class MaterialData implements ResponseDataListner {

    private int fuel;
    private int ammo;
    private int steel;
    private int bauxite;
    private int instantConstruction;
    private int instantRepair;
    private int developmentMaterial;
    private int moddingMaterial;

    @Override
    public void loadFromResponse(String apiName, JsonElement data) {
        switch (apiName) {
            case "api_port/port":
            case "api_get_member/material":
                JsonArray json = data.getAsJsonArray();
//                fuel = json.getJSONObject(0).getIntValue("api_value");
//                ammo = json.getJSONObject(1).getIntValue("api_value");
//                steel = json.getJSONObject(2).getIntValue("api_value");
//                bauxite = json.getJSONObject(3).getIntValue("api_value");
//                instantConstruction = json.getJSONObject(4).getIntValue("api_value");
//                instantRepair = json.getJSONObject(5).getIntValue("api_value");
//                developmentMaterial = json.getJSONObject(6).getIntValue("api_value");
//                moddingMaterial = json.getJSONObject(7).getIntValue("api_value");
                break;

            case "api_req_hokyu/charge":
            case "api_req_kousyou/destroyship":
//                Fuel = (int)data[0];
//                Ammo = (int)data[1];
//                Steel = (int)data[2];
//                Bauxite = (int)data[3];
                break;

            case "api_req_kousyou/destroyitem2":
//                Fuel += (int)data.api_get_material[0];
//                Ammo += (int)data.api_get_material[1];
//                Steel += (int)data.api_get_material[2];
//                Bauxite += (int)data.api_get_material[3];
                break;

            case "api_req_kousyou/createitem":
            case "api_req_kousyou/remodel_slot":
//                Fuel = (int)data[0];
//                Ammo = (int)data[1];
//                Steel = (int)data[2];
//                Bauxite = (int)data[3];
//                InstantConstruction = (int)data[4];
//                InstantRepair = (int)data[5];
//                DevelopmentMaterial = (int)data[6];
//                ModdingMaterial = (int)data[7];
                break;
        }
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getSteel() {
        return steel;
    }

    public void setSteel(int steel) {
        this.steel = steel;
    }

    public int getBauxite() {
        return bauxite;
    }

    public void setBauxite(int bauxite) {
        this.bauxite = bauxite;
    }

    public int getInstantConstruction() {
        return instantConstruction;
    }

    public void setInstantConstruction(int instantConstruction) {
        this.instantConstruction = instantConstruction;
    }

    public int getInstantRepair() {
        return instantRepair;
    }

    public void setInstantRepair(int instantRepair) {
        this.instantRepair = instantRepair;
    }

    public int getDevelopmentMaterial() {
        return developmentMaterial;
    }

    public void setDevelopmentMaterial(int developmentMaterial) {
        this.developmentMaterial = developmentMaterial;
    }

    public int getModdingMaterial() {
        return moddingMaterial;
    }

    public void setModdingMaterial(int moddingMaterial) {
        this.moddingMaterial = moddingMaterial;
    }
}