package kandroid.data.battle

import com.google.gson.JsonElement
import kandroid.data.ResponseDataListener

class BattleNormalDay : BattleDay(), ResponseDataListener {
    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
    }
}