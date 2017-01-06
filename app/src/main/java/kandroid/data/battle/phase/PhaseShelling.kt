package kandroid.data.battle.phase

import com.google.gson.JsonElement
import kandroid.data.battle.BattleData
import kandroid.data.battle.phase.commoms.CommonShelling
import kandroid.utils.json.get

class PhaseShelling(battle: BattleData, val type: Type) : BasePhase(battle.data) {

    enum class Type {
        OpeningASW {
            override val jsonKey: String get() = "api_opening_taisen"
        },
        FirstFire {
            override val jsonKey: String get() = "api_hougeki1"
        },
        SecondFire {
            override val jsonKey: String get() = "api_hougeki2"
        },
        ThirdFire {
            override val jsonKey: String get() = "api_hougeki3"
        };

        abstract val jsonKey: String
    }

    val shellingData = DayShelling()

    inner class DayShelling : CommonShelling() {
        override val data: JsonElement get() = this@PhaseShelling.data[type.jsonKey]
    }
}