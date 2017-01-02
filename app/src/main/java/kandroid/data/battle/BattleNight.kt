package kandroid.data.battle

import kandroid.data.battle.phase.PhaseNightBattle

class BattleNight(battleMode: BattleModes) : BattleBase(battleMode) {
    var nightBattle: PhaseNightBattle? = null
}
