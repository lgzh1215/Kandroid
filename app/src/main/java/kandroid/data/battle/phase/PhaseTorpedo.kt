package kandroid.data.battle.phase

import com.google.gson.JsonElement
import kandroid.data.battle.BattleData
import kandroid.utils.json.get
import kandroid.utils.json.list

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

    val torpedoData: JsonElement get() = data[type.jsonKey]

    val fTorpedo: List<Int> get() = data["api_frai"].list()
    val eTorpedo: List<Int> get() = data["api_erai"].list()
    val fDamage: List<Int> get() = data["api_fdam"].list()
    val eDamage: List<Int> get() = data["api_edam"].list()
    val fYDamage: List<Int> get() = data["api_fydam"].list()
    val eYDamage: List<Int> get() = data["api_eydam"].list()
    val fCl: List<Int> get() = data["api_fcl"].list()
    val eCl: List<Int> get() = data["api_ecl"].list()
}