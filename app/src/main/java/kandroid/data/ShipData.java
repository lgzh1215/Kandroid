package kandroid.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.List;

import kandroid.data.Start2Data.MasterShipData;
import kandroid.observer.POJO;
import kandroid.utils.CatException;
import kandroid.utils.Identifiable;
import kandroid.utils.Utils;

public class ShipData extends Data<ShipData.ApiShip> implements Identifiable {

    public int getMasterID() {
        return data.api_id;
    }

    public int getSortID() {
        return data.api_sortno;
    }

    public int getShipID() {
        return data.api_ship_id;
    }

    public int getLevel() {
        return data.api_lv;
    }

    public int getExpTotal() {
        return data.api_exp.get(0);
    }

    public int getExpNext() {
        return data.api_exp.get(1);
    }

    public int getHPCurrent() {
        return data.api_nowhp;
    }

    public int getHPMax() {
        return data.api_maxhp;
    }

    public int getRange() {
        return data.api_leng;
    }

    public int[] getSlot() {
        return Utils.listToArray(data.api_slot);
    }

    public int[] getSlotMaster() {
        throw new UnsupportedOperationException();
    }

    public EquipmentData[] getSlotInstance() {
        throw new UnsupportedOperationException();
    }

    public Start2Data.MasterEquipmentData[] getSlotInstanceMaster() {
        throw new UnsupportedOperationException();
    }

    public int getExpansionSlot() {
        return data.api_slot_ex;
    }

    public int getExpansionSlotMaster() {
        throw new UnsupportedOperationException();
    }

    public EquipmentData getExpansionSlotInstance() {
        throw new UnsupportedOperationException();
    }

    public Start2Data.MasterEquipmentData getExpansionSlotInstanceMaster() {
        throw new UnsupportedOperationException();
    }

    public int[] getAllSlot() {
        throw new UnsupportedOperationException();
    }

    public int[] getAllSlotMaster() {
        throw new UnsupportedOperationException();
    }

    public EquipmentData[] getAllSlotInstance() {
        throw new UnsupportedOperationException();
    }

    public Start2Data.MasterEquipmentData[] getAllSlotInstanceMaster() {
        throw new UnsupportedOperationException();
    }

    public int[] getAircraft() {
        return Utils.listToArray(data.api_onslot);
    }

    public int getAircraftTotal() {
        int total = 0;
        for (int i : getAircraft()) {
            total += i;
        }
        return total;
    }

    public int getFuel() {
        return data.api_fuel;
    }

    public int getAmmo() {
        return data.api_bull;
    }

    public int getSlotSize() {
        return data.api_slotnum;
    }

    public int getRepairTime() {
        return data.api_ndock_time;
    }

    public int getRepairSteel() {
        return data.api_ndock_item.get(1);
    }

    public int getRepairFuel() {
        return data.api_ndock_item.get(0);
    }

    public int getCondition() {
        return data.api_cond;
    }

    public int getFirepowerModernized() {
        return data.api_kyouka.get(0);
    }

    public int getTorpedoModernized() {
        return data.api_kyouka.get(1);
    }

    public int getAAModernized() {
        return data.api_kyouka.get(2);
    }

    public int getArmorModernized() {
        return data.api_kyouka.get(3);
    }

    public int getLuckModernized() {
        return data.api_kyouka.get(4);
    }

    public int getFirepowerRemain() {
        MasterShipData master = getMasterShip();
        return master.getFirepowerMax() - master.getFirepowerMin() - getFirepowerModernized();
    }

    public int getTorpedoRemain() {
        MasterShipData master = getMasterShip();
        return master.getTorpedoMax() - master.getTorpedoMin() - getTorpedoModernized();
    }

    public int getAARemain() {
        MasterShipData master = getMasterShip();
        return master.getAAMax() - master.getAAMin() - getAAModernized();
    }

    public int getArmorRemain() {
        MasterShipData master = getMasterShip();
        return master.getArmorMax() - master.getArmorMin() - getArmorModernized();
    }

    public int getLuckRemain() {
        MasterShipData master = getMasterShip();
        return master.getLuckMax() - master.getLuckMin() - getLuckModernized();
    }

    public int getFirepowerTotal() {
        return data.api_karyoku.get(0);
    }

    public int getTorpedoTotal() {
        return data.api_raisou.get(0);
    }

    public int getAATotal() {
        return data.api_taiku.get(0);
    }

    public int getArmorTotal() {
        return data.api_soukou.get(0);
    }

    public int getEvasionTotal() {
        return data.api_kaihi.get(0);
    }

    public int getASWTotal() {
        return data.api_taisen.get(0);
    }

    public int getLOSTotal() {
        return data.api_sakuteki.get(0);
    }

    public int getLuckTotal() {
        return data.api_lucky.get(0);
    }

    public int getBomberTotal() {
        throw new UnsupportedOperationException();
    }

    public int getFirepowerBase() {
        return getMasterShip().getFirepowerMin() + getFirepowerModernized();
    }

    public int getTorpedoBase() {
        return getMasterShip().getTorpedoMin() + getTorpedoModernized();
    }

    public int getAABase() {
        return getMasterShip().getAAMin() + getAAModernized();
    }

    public int getArmorBase() {
        return getMasterShip().getArmorMin() + getArmorModernized();
    }

    public int getEvasionBase() {
        throw new UnsupportedOperationException();
    }

    public int getASWBase() {
        throw new UnsupportedOperationException();
    }

    public int getLOSBase() {
        throw new UnsupportedOperationException();
    }

    public int getLuckBase() {
        return getMasterShip().getLuckMin() + getLuckModernized();
    }

    public int getEvasionMax() {
        return data.api_kaihi.get(1);
    }

    public int getASWMax() {
        return data.api_taisen.get(1);
    }

    public int getLOSMax() {
        return data.api_sakuteki.get(1);
    }

    public int getBombTotal() {
        throw new UnsupportedOperationException();
    }

    public boolean isLocked() {
        return data.api_locked != 0;
    }

    public boolean isLockedByEquipment() {
        return data.api_locked_equip != 0;
    }

    public int getSallyArea() {
        return data.api_sally_area;
    }

    public MasterShipData getMasterShip() {
        return KCDatabase.getMaster().masterShipData.get(getShipID());
    }

    public int getRepairingDockID() {
        throw new UnsupportedOperationException();
    }

    public int getFleet() {
        throw new UnsupportedOperationException();
    }

    public String getFleetWithIndex() {
        throw new UnsupportedOperationException();
    }

    public boolean isMarried() {
        return getLevel() > 99;
    }

    public int getExpNextRemodel() {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        return getMasterShip().getName();
    }

    public String getNameWithLevel() {
        throw new UnsupportedOperationException();
    }

    public double getHPRate() {
        throw new UnsupportedOperationException();
    }

    public int getFuelMax() {
        return getMasterShip().getFuel();
    }

    public int getAmmoMax() {
        return getMasterShip().getAmmo();
    }

    public double getFuelRate() {
        throw new UnsupportedOperationException();
    }

    public double getAmmoRate() {
        throw new UnsupportedOperationException();
    }

    public List<Double> getAircraftRate() {
        throw new UnsupportedOperationException();
    }

    public double getAircraftTotalRate() {
        throw new UnsupportedOperationException();
    }

    public boolean isExpansionSlotAvailable() {
        return getExpansionSlot() != 0;
    }

    public void loadFromResponse(String apiName, JsonElement data) {
        switch (apiName) {
            case "api_port/port":
                setData(new Gson().fromJson(data, ApiShip.class));
                break;
            default:
                throw new CatException();
        }
    }

    public void repair() {
        data.api_nowhp = getHPMax();
        data.api_cond = Math.max(getCondition(), 40);

        data.api_ndock_time = 0;
        data.api_ndock_item.set(0, 0);
        data.api_ndock_item.set(1, 0);
    }

    @Override
    public int getID() {
        return data.api_id;
    }

    public static class ApiShip extends POJO {
        public int api_id;
        public int api_sortno;
        public int api_ship_id;
        public int api_lv;
        public int api_nowhp;
        public int api_maxhp;
        public int api_leng;
        public int api_slot_ex;
        public int api_backs;
        public int api_fuel;
        public int api_bull;
        public int api_slotnum;
        public int api_ndock_time;
        public int api_srate;
        public int api_cond;
        public int api_locked;
        public int api_locked_equip;
        public List<Integer> api_exp;
        public List<Integer> api_slot;
        public List<Integer> api_onslot;
        public List<Integer> api_kyouka;
        public List<Integer> api_ndock_item;
        public List<Integer> api_karyoku;
        public List<Integer> api_raisou;
        public List<Integer> api_taiku;
        public List<Integer> api_soukou;
        public List<Integer> api_kaihi;
        public List<Integer> api_taisen;
        public List<Integer> api_sakuteki;
        public List<Integer> api_lucky;
        public int api_sally_area = -1;
    }
}
