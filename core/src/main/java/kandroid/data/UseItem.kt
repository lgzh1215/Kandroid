package kandroid.data

import kandroid.data.JsonWrapper
import kandroid.utils.Identifiable
import kandroid.utils.json.get
import kandroid.utils.json.int

class UseItem : JsonWrapper(), Identifiable {

    val itemId: Int get() = data["api_id"].int()
    val count: Int get() = data["api_count"].int()

    override val id: Int get() = itemId
}