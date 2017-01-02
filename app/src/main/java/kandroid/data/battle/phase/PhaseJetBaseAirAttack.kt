package kandroid.data.battle.phase

import kandroid.data.battle.BattleBase

class PhaseJetBaseAirAttack(battle: BattleBase) : BasePhase(battle) {
    override val isAvailable: Boolean
        get() = throw UnsupportedOperationException()
}