package kandroid.data.battle.phase.commoms

import com.google.gson.JsonElement
import kandroid.utils.json.array
import kandroid.utils.json.get
import kandroid.utils.json.list

abstract class CommonShelling {

    abstract val data: JsonElement

    /**
     * 攻击方区分敌我Flag
     * 0=我方, 1=敌方
     */
    val actionFriendEnemyFlag : List<Int> get() = data["api_at_eflag"].list()
    /**
     * 攻击方
     */
    val actionOrder: List<Int> get() = data["api_at_list"].list()
    /**
     * 昼战攻击种类
     * 0=平A, 1=???, 2=二连, 3=主副CI, 4=主电CI, 5=主彻CI, 6=主主CI
     */
    val actionType: List<Int> get() = data["api_at_type"].list()
    /**
     * 夜战攻击种类
     * 0=平A, 1=二连, 2=炮雷CI, 3=雷雷CI, 4=主副CI, 5=主主CI
     */
    val actionTypeNight: List<Int> get() = data["api_sp_list"].list()
    /**
     * 防守方
     */
    val defenceOrder: List<List<Int>> get() = data["api_df_list"].array!!.map { it.list<Int>() }
    /**
     * 暴击Flag
     * 0=Miss, 1=命中, 2=暴击
     */
    val critical: List<List<Int>> get() = data["api_cl_list"].array!!.map { it.list<Int>() }
    /**
     * 伤害
     */
    val damage: List<List<Int>> get() = data["api_damage"].array!!.map { it.list<Int>() }
}