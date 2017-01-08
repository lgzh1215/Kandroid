package kandroid.data.battle

import com.google.gson.JsonElement
import kandroid.data.battle.phase.PhaseNightBattle

class BattleNight(val day: BattleDay? = null, battleMode: BattleModes) : BattleData(battleMode) {
    var nightBattle: PhaseNightBattle? = null

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        super.loadFromResponse(apiName, responseData)
        nightBattle = PhaseNightBattle(this)
    }
}
