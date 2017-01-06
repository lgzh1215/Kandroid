package kandroid.data.battle

import com.google.gson.JsonElement
import kandroid.data.JsonWrapper
import kandroid.data.battle.phase.PhaseInitial
import kandroid.data.battle.phase.PhaseSearching

abstract class BattleData : JsonWrapper() {
    var initial: PhaseInitial? = null
    var searching: PhaseSearching? = null

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        super.loadFromResponse(apiName, responseData)
        initial = PhaseInitial(this)
        searching = PhaseSearching(this)
    }
}