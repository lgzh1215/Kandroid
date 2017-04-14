package kandroid.data.battle.phase

import com.google.gson.JsonElement
import kandroid.data.battle.BattleData
import kandroid.data.battle.phase.commoms.CommonAirStrike
import kandroid.utils.exception.CatException
import kandroid.utils.json.array
import kandroid.utils.json.get
import kandroid.utils.json.jsonNull
import java.util.*

class PhaseAirBattle(battle: BattleData, val type: Type) : BasePhase(battle.data) {

    enum class Type {
        JetBase {
            override val jsonKey: String get() = "api_air_base_injection"
        },
        Jet {
            override val jsonKey: String get() = "api_injection_kouku"
        },
        NormalBase {
            override val jsonKey: String get() = "api_air_base_attack"
        },
        Normal {
            override val jsonKey: String get() = "api_kouku"
        },
        AirRaid {
            override val jsonKey: String get() = "api_air_base_attack"
        };

        abstract val jsonKey: String
    }

    val airStrikes: ArrayList<CommonAirStrike> = ArrayList()

    init {
        when (type) {
            Type.JetBase -> {
                airStrikes.add(JetBaseAirStrike(data[type.jsonKey]))
            }
            Type.Jet -> {
                airStrikes.add(JetAirStrike(data[type.jsonKey]))
            }
            Type.NormalBase -> {
                data[type.jsonKey].array?.mapTo(airStrikes, ::NormalBaseAirStrike)
            }
            Type.Normal -> {
                airStrikes.add(NormalAirStrike(data[type.jsonKey], data["api_stage_flag"]))
                // 如果有第二波航空战
                if (data["api_stage_flag2"] !== jsonNull) {
                    airStrikes.add(NormalAirStrike(data["api_kouku2"], data["api_stage_flag2"]))
                }
            }
            Type.AirRaid -> {
                airStrikes.add(AirRaidAirStrike(data[type.jsonKey]))
            }
            else -> CatException()
        }
    }

    class JetBaseAirStrike(override val data: JsonElement) : CommonAirStrike() {
        override val planes: List<BaseAirCorpsPlaneData>
            get() = data["api_air_base_data"].array!!.map(::BaseAirCorpsPlaneData)
    }

    class JetAirStrike(override val data: JsonElement) : CommonAirStrike()

    class NormalBaseAirStrike(override val data: JsonElement) : CommonAirStrike() {
        override val planes: List<BaseAirCorpsPlaneData>
            get() = data["api_squadron_plane"].array!!.map(::BaseAirCorpsPlaneData)
    }

    class NormalAirStrike(override val data: JsonElement,
                          override val api_stage_flag: JsonElement) : CommonAirStrike()

    class AirRaidAirStrike(override val data: JsonElement) : CommonAirStrike() {
        override val planes: List<BaseAirCorpsPlaneData>
            get() = data["api_squadron_plane"].array!!.filter { it.isJsonArray }
                    .map(::BaseAirCorpsPlaneData)
    }
}