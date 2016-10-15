package kandroid.data

import com.google.gson.JsonElement
import kandroid.utils.Identifiable
import kandroid.utils.json.get
import kandroid.utils.json.int
import kandroid.utils.json.long
import java.util.*

class DockData : JsonWrapper(), Identifiable {

    val dockId: Int get() = data["api_id"].int()

    val state: Int get() = data["api_state"].int()

    val shipId: Int get() = data["api_ship_id"].int()

    val completionTime: Date get() = Date(data["api_complete_time"].long())

    override val id: Int get() = dockId

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        val state = this.state
        val shipId = this.shipId
        super.loadFromResponse(apiName, responseData)
        val newState = this.state
        if (state == 1 && newState == 0 && shipId != 0) {
            KCDatabase.ships[shipId].repair()
        }
    }
}