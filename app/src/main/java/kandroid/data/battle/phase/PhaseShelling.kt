package kandroid.data.battle.phase

import kandroid.data.battle.BattleBase

class PhaseShelling(battle: BattleBase, val type: Type) : BasePhase(battle) {

    enum class Type {
        OpeningASW, FirstFire, SecondFire, ThirdFire
    }

    override val isAvailable: Boolean
        get() = throw UnsupportedOperationException()
}