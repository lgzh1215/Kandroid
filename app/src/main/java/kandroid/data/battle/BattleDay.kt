package kandroid.data.battle

import com.google.gson.JsonElement
import kandroid.data.battle.phase.PhaseAirBattle
import kandroid.data.battle.phase.PhaseShelling
import kandroid.data.battle.phase.PhaseSupport
import kandroid.data.battle.phase.PhaseTorpedo
import kandroid.utils.json.array
import kandroid.utils.json.int
import kandroid.utils.json.obj

class BattleDay : BattleData() {

    var jetBaseAirAttack: PhaseAirBattle? = null
    var jetAirBattle: PhaseAirBattle? = null
    var baseAirAttack: PhaseAirBattle? = null
    var airBattle: PhaseAirBattle? = null
    var support: PhaseSupport? = null
    var openingASW: PhaseShelling? = null
    var openingTorpedo: PhaseTorpedo? = null
    var shelling1: PhaseShelling? = null
    var shelling2: PhaseShelling? = null
    var torpedo: PhaseTorpedo? = null
    var shelling3: PhaseShelling? = null

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        super.loadFromResponse(apiName, responseData)
        val data = responseData.obj ?: return

        if (data.has("api_air_base_injection")) {
            jetBaseAirAttack = PhaseAirBattle(this, PhaseAirBattle.Type.JetBase)
        }

        if (data.has("api_injection_kouku")) {
            jetAirBattle = PhaseAirBattle(this, PhaseAirBattle.Type.Jet)
        }

        if (data.has("api_air_base_attack")) {
            baseAirAttack = PhaseAirBattle(this, PhaseAirBattle.Type.NormalBase)
        }

        if (data.has("api_stage_flag")) {
            airBattle = PhaseAirBattle(this, PhaseAirBattle.Type.Normal)
        }

        if (data["api_support_flag"].int() != 0) {
            support = PhaseSupport(this)
        }

        if (data["api_opening_taisen_flag"].int() != 0) {
            openingASW = PhaseShelling(this, PhaseShelling.Type.OpeningASW)
        }

        if (data["api_opening_flag"].int() != 0) {
            openingTorpedo = PhaseTorpedo(this, PhaseTorpedo.Type.Opening)
        }

        val api_hourai_flag = data["api_hourai_flag"].array
        if (api_hourai_flag != null) {

            if (api_hourai_flag[0].int() != 0) {
                shelling1 = PhaseShelling(this, PhaseShelling.Type.FirstFire)
            }

            if (api_hourai_flag[1].int() != 0) {
                shelling2 = PhaseShelling(this, PhaseShelling.Type.SecondFire)
            }

            if (api_hourai_flag[2].int() != 0) {
                shelling3 = PhaseShelling(this, PhaseShelling.Type.ThirdFire)
            }

            if (api_hourai_flag[3].int() != 0) {
                torpedo = PhaseTorpedo(this, PhaseTorpedo.Type.Ending)
            }
        }
    }
}