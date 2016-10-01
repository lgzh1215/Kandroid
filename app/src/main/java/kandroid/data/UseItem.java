package kandroid.data;

import kandroid.observer.POJO;
import kandroid.utils.Identifiable;

public class UseItem extends Data<UseItem.ApiUseitem> implements Identifiable{

    public int getItemID() {
        return data.api_id;
    }

    public int getCount() {
        return data.api_count;
    }

    @Override
    public int getID() {
        return data.api_id;
    }

    public static class ApiUseitem extends POJO {
        public int api_id;
        public int api_count;
    }
}
