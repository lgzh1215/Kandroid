package kandroid.data.battle.phase

import com.google.gson.JsonElement
import kandroid.data.battle.BattleData
import kandroid.data.battle.phase.commoms.CommonTorpedo
import kandroid.utils.json.get

class PhaseTorpedo(battle: BattleData, val type: Type) : BasePhase(battle.data) {

    enum class Type {
        Opening {
            override val jsonKey: String get() = "api_opening_atack"
        },
        Ending {
            override val jsonKey: String get() = "api_raigeki"
        };

        abstract val jsonKey: String
    }

    val torpedo = Torpedo()

    inner class Torpedo : CommonTorpedo() {
        override val data: JsonElement get() = data[type.jsonKey]
    }
}