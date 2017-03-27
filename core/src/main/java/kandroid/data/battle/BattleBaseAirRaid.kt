package kandroid.data.battle

import com.google.gson.JsonElement
import kandroid.data.battle.phase.PhaseAirBattle
import kandroid.utils.json.get
import kandroid.utils.json.int
import kandroid.utils.json.obj

class BattleBaseAirRaid : BattleData(BattleModes.BaseAirRaid) {
    var airBattle: PhaseAirBattle? = null

    /**
     * 抄家空襲被害的类别
     * 1=資源に被害, 2=資源・航空隊に被害, 3=航空隊に被害, 4=損害なし
     */
    val airRaidDamageKind: Int get() = data["api_lost_kind"].int()

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        super.loadFromResponse(apiName, responseData)
        val data = responseData.obj ?: return
        if (data.has("api_destruction_battle")) {
            airBattle = PhaseAirBattle(this, PhaseAirBattle.Type.AirRaid)
        }
    }
}