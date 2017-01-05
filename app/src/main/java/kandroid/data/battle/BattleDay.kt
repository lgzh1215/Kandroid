package kandroid.data.battle

import com.google.gson.JsonElement
import kandroid.data.battle.phase.*
import kandroid.utils.json.array
import kandroid.utils.json.int
import kandroid.utils.json.obj

class BattleDay(battleMode: BattleModes) : BattleBase(battleMode) {

    var jetBaseAirAttack: PhaseJetBaseAirAttack? = null
    var jetAirBattle: PhaseJetAirBattle? = null
    var baseAirAttack: PhaseBaseAirAttack? = null
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
        val data = responseData.obj!!

        if (data.has("api_air_base_injection")) {
            jetBaseAirAttack = PhaseJetBaseAirAttack(this)
        }

        if (data.has("api_injection_kouku")) {
            jetAirBattle = PhaseJetAirBattle(this)
        }

        if (data.has("api_air_base_attack")) {
            baseAirAttack = PhaseBaseAirAttack(this)
        }

        airBattle = PhaseAirBattle(this)

        if (data["api_support_flag"].int() != 0) {
            support = PhaseSupport(this)
        }

        if (data["api_opening_taisen_flag"].int() != 0) {
            openingASW = PhaseShelling(this, PhaseShelling.Type.OpeningASW)
        }

        if (data["api_opening_flag"].int() != 0) {
            openingTorpedo = PhaseTorpedo(this)
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
                torpedo = PhaseTorpedo(this)
            }
        }
    }
}