package kandroid.data;

import kandroid.observer.POJO;
import kandroid.utils.Identifiable;

public class EquipmentData extends Data<EquipmentData.ApiSlotItem> implements Identifiable {

    public int getMasterID() {
        return data.api_id;
    }

    public int getEquipmentID() {
        return data.api_slotitem_id;
    }

    public boolean isLocked() {
        return data.api_locked != 0;
    }

    public int getLevel() {
        return data.api_level;
    }

    public int getAircraftLevel() {
        return data.api_alv;
    }

    public Start2Data.MasterEquipmentData getMasterEquipment() {
        return KCDatabase.INSTANCE.getMaster().masterEquipmentData.get(getMasterID());
    }

    public String getName() {
        return getMasterEquipment().getName();
    }

    public String getNameWithLevel() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getId() {
        return data.api_id;
    }

    public static class ApiSlotItem extends POJO {
        public int api_id;
        public int api_slotitem_id;
        public int api_locked;
        public int api_level;
        public int api_alv;
    }
}
