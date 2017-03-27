package kandroid.data.battle.phase

import kandroid.data.battle.BattleData
import kandroid.utils.json.get
import kandroid.utils.json.int

class PhaseSearching(battle: BattleData) : BasePhase(battle.data) {

    /**
     * 自军索敌结果
     * 1=成功, 2=成功(索敌机未归还), 3=未归还, 4=失败, 5=成功(索敵機なし), 6=失敗(艦載機使用せず)
     */
    val searchingFriend: Int get() = data["api_search"][0].int(-1)
    /**
     * 敌军索敌结果
     */
    val searchingEnemy: Int get() = data["api_search"][1].int(-1)
    /**
     * 自军阵型
     */
    val formationFriend: Int get() = data["api_formation"][0].int()
    /**
     * 敌军阵型
     */
    val formationEnemy: Int get() = data["api_formation"][1].int()
    /**
     * 航向(交战形态)
     */
    val engagementForm: Int get() = data["api_formation"][2].int()

}