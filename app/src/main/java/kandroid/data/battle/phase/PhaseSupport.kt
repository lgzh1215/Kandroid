package kandroid.data.battle.phase

import com.google.gson.JsonElement
import kandroid.data.battle.BattleData
import kandroid.data.battle.phase.commoms.CommonAirStrike
import kandroid.utils.json.get
import kandroid.utils.json.int
import kandroid.utils.json.list

class PhaseSupport(battle: BattleData) : BasePhase(battle.data) {

    /**
     * 支援舰队攻击Flag
     * 0=无/旅游, 1=航空支援, 2=炮击支援, 3=雷击支援
     */
    val supportFlag: Int get() = data["api_support_flag"].int()

    val airSupport = AirSupport()
    val shellingSupport = ShellingOrTorpedoSupport()
    val torpedoSupport : ShellingOrTorpedoSupport get() = shellingSupport

    interface BaseSupport {
        val data: JsonElement
        /**
         * 支援舰队ID
         */
        val fleetId: Int get() = data["api_deck_id"].int()
        /**
         * 支援舰娘ID
         */
        val shipId: List<Int> get() = data["api_ship_id"].list()
        /**
         * 中破图Flag, 仅Banner显示中破, 旗舰立绘不变
         * 0=通常, 1=中破
         */
        val undressingFlag: List<Int> get() = data["api_undressing_flag"].list()
    }

    inner class AirSupport : CommonAirStrike(), BaseSupport {
        override val data: JsonElement get() = this@PhaseSupport.data["api_support_info"]["api_support_airatack"]
        override val api_stage_flag: JsonElement get() = data["api_stage_flag"]
    }

    inner class ShellingOrTorpedoSupport : BaseSupport {
        override val data: JsonElement get() = data["api_support_info"]["api_support_hourai"]
        /**
         * 暴击Flag
         * 0=Miss, 1=命中, 2=暴击
         */
        val critical: List<Int> get() = data["api_cl_list"].list()
        /**
         * 造成伤害
         */
        val damage: List<Int> get() = data["api_damage"].list()
    }
}