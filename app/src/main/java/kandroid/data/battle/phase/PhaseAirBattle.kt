package kandroid.data.battle.phase

import com.google.gson.JsonElement
import kandroid.data.battle.BattleBase
import kandroid.utils.json.get
import kandroid.utils.json.int
import kandroid.utils.json.list
import kandroid.utils.json.obj

class PhaseAirBattle(battle: BattleBase, val type: Type) : BasePhase(battle) {

    enum class Type {
        Jet {
            override val key: String get() = "api_injection_kouku"
        },
        Normal {
            override val key: String get() = "api_kouku"
        };

        abstract val key: String
    }

    val hasSecondAirBattle: Boolean get() = if (type != Type.Normal) false
    else (data.obj?.has("api_stage_flag2") ?: false)

    val first = AirBattle("api_stage_flag", type.key)

    val second = AirBattle("api_stage_flag2", "api_kouku2")

    inner class AirBattle(val stageFlagKey: String, val koukuKey: String) {

        val stateFlag: List<Int> get() = data[stageFlagKey].list()

        val fPlaneFrom: List<Int> get() = data[koukuKey]["api_plane_from"][0].list()
        val ePlaneFrom: List<Int> get() = data[koukuKey]["api_plane_from"][1].list()

        val state1 = State1()
        val state2 = State2()
        val state3 = State3()

        inner class State1 {
            val data: JsonElement get() = this@PhaseAirBattle.data[koukuKey]["api_stage1"]

            val fCount: Int get() = data["api_f_count"].int()
            val fLostCount: Int get() = data["api_f_lostcount"].int()
            val eCount: Int get() = data["api_e_count"].int()
            val eLostCount: Int get() = data["api_e_lostcount"].int()

            val airSuperiority: Int get() = data["api_disp_seiku"].int()
            val touchPlane: List<Int> get() = data["api_touch_plane"].list()
        }

        inner class State2 {
            val data: JsonElement get() = this@PhaseAirBattle.data[koukuKey]["api_stage2"]

            val fCount: Int get() = data["api_f_count"].int()
            val fLostCount: Int get() = data["api_f_lostcount"].int()
            val eCount: Int get() = data["api_e_count"].int()
            val eLostCount: Int get() = data["api_e_lostcount"].int()

            val aaCutinShipIndex: Int get() = data["api_air_fire"]["api_idx"].int()
            val aaCutinKind: Int get() = data["api_air_fire"]["api_kind"].int()
            val aaCutinEquipmentMasterId: List<Int> get() = data["api_air_fire"]["api_use_items"].list()
        }

        inner class State3 {
            val data: JsonElement get() = this@PhaseAirBattle.data[koukuKey]["api_stage3"]

            val fClFlag: List<Int> get() = data["api_fcl_flag"].list<Int>().drop(1)
            val eClFlag: List<Int> get() = data["api_ecl_flag"].list<Int>().drop(1)

            val fDamage: List<Int> get() = data["api_fdam"].list<Int>().drop(1)
            val eDamage: List<Int> get() = data["api_edam"].list<Int>().drop(1)
        }
    }
}