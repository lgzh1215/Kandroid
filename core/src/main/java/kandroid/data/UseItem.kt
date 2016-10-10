package kandroid.data

import kandroid.observer.POJO
import kandroid.utils.Identifiable

class UseItem : Data<UseItem.ApiUseitem>(), Identifiable {

    val itemID: Int
        get() = data.api_id

    val count: Int
        get() = data.api_count

    override val id: Int
        get() = data.api_id

    open class ApiUseitem : POJO() {
        var api_id: Int = 0
        var api_count: Int = 0
    }
}
