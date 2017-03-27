package kandroid.data.battle.phase.commoms

import com.google.gson.JsonElement
import kandroid.utils.json.get
import kandroid.utils.json.list

abstract class CommonTorpedo {

    abstract val data: JsonElement

    /**
     * 我方雷击目标
     */
    val fTorpedoTarget: List<Int> get() = data["api_frai"].list()
    /**
     * 我方打出的伤害
     */
    val fDamageGive: List<Int> get() = data["api_fydam"].list()
    /**
     * 我方攻击的暴击Flag
     * 0=Miss, 1=命中, 2=暴击
     */
    val fCritical: List<Int> get() = data["api_fcl"].list()

    /**
     * 敌方雷击目标
     */
    val eTorpedoTarget: List<Int> get() = data["api_erai"].list()
    /**
     * 敌方打出的伤害
     */
    val eDamageGive: List<Int> get() = data["api_eydam"].list()
    /**
     * 敌方攻击的暴击Flag
     * 0=Miss, 1=命中, 2=暴击
     */
    val eCritical: List<Int> get() = data["api_ecl"].list()

    /**
     * 我方受到的伤害
     */
    val fDamageReceive: List<Int> get() = data["api_fdam"].list()
    /**
     * 敌方受到的伤害
     */
    val eDamageReceive: List<Int> get() = data["api_edam"].list()
}