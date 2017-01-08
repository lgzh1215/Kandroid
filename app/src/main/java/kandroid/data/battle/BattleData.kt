package kandroid.data.battle

import com.google.gson.JsonElement
import kandroid.data.JsonWrapper
import kandroid.data.battle.phase.PhaseParameters
import kandroid.data.battle.phase.PhaseSearching

abstract class BattleData(val battleMode: BattleModes) : JsonWrapper() {
    var parameters: PhaseParameters? = null
    var searching: PhaseSearching? = null

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        super.loadFromResponse(apiName, responseData)
        parameters = PhaseParameters(this)
        searching = PhaseSearching(this)
    }
}