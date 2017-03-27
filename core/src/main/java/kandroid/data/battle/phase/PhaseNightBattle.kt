package kandroid.data.battle.phase

import com.google.gson.JsonElement
import kandroid.data.battle.BattleData
import kandroid.data.battle.phase.commoms.CommonShelling
import kandroid.utils.json.get
import kandroid.utils.json.int

class PhaseNightBattle(battle: BattleData) : BasePhase(battle.data) {

    /**
     * 我方夜间接触飞机
     */
    val fTouchPlane: Int get() = data["api_touch_plane"][0].int()
    /**
     * 敌方夜间接触飞机
     */
    val eTouchPlane: Int get() = data["api_touch_plane"][1].int()

    /**
     * 我方照明弹发射舰
     */
    val fFlarePosition: Int get() = data["api_flare_pos"][0].int()
    /**
     * 敌方照明弹发射舰
     */
    val eFlarePosition: Int get() = data["api_flare_pos"][1].int()

    /**
     * 夜战数据
     */
    val nightShelling = NightShelling()

    inner class NightShelling : CommonShelling() {
        override val data: JsonElement get() = this@PhaseNightBattle.data["api_hougeki"]
    }
}