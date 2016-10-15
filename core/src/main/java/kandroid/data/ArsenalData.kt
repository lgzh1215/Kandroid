package kandroid.data

import kandroid.data.JsonWrapper
import kandroid.utils.Identifiable
import kandroid.utils.json.get
import kandroid.utils.json.int
import kandroid.utils.json.long
import java.util.*

class ArsenalData : JsonWrapper(), Identifiable {

    val arsenalId: Int get() = data["api_id"].int()

    val state: Int get() = data["api_state"].int()

    val shipID: Int get() = data["api_created_ship_id"].int()

    val completionTime: Date get() = Date(data["api_complete_time"].long())

    val fuel: Int get() = data["api_item1"].int()

    val ammo: Int get() = data["api_item2"].int()

    val steel: Int get() = data["api_item3"].int()

    val bauxite: Int get() = data["api_item4"].int()

    val developmentMaterial: Int get() = data["api_item5"].int()

    override val id: Int get() = arsenalId
}
