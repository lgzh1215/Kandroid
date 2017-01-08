package kandroid.data.battle.phase.commoms

import com.google.gson.JsonElement
import kandroid.utils.json.get
import kandroid.utils.json.int
import kandroid.utils.json.jsonNull
import kandroid.utils.json.list

abstract class CommonAirStrike {

    abstract val data: JsonElement

    open val api_stage_flag: JsonElement get() = data["api_stage_flag"]

    val hasS1: Boolean get() = if (api_stage_flag !== jsonNull) api_stage_flag[1].int() != 0 else s1.isAvailable
    val hasS2: Boolean get() = if (api_stage_flag !== jsonNull) api_stage_flag[2].int() != 0 else s2.isAvailable
    val hasS3: Boolean get() = if (api_stage_flag !== jsonNull) api_stage_flag[3].int() != 0 else s3.isAvailable

    val fPlaneFrom: List<Int> get() = data["api_plane_from"][0].list()
    val ePlaneFrom: List<Int> get() = data["api_plane_from"][1].list()

    /**
     * Stage1 空对空攻击
     */
    val s1 = Stage1()
    /**
     * Stage2 舰对空攻击
     */
    val s2 = Stage2()
    /**
     * Stage3 空对舰攻击
     */
    val s3 = Stage3()
    /**
     * Stage3 空对舰攻击,联合舰队二队部分
     */
    val s3c = Stage3Combined()

    /**
     * 陆航起飞的飞机
     */
    open val planes: List<BaseAirCorpsPlaneData> get() = emptyList()

    class BaseAirCorpsPlaneData(val data: JsonElement) {
        val count: Int get() = data["api_count"].int()
        val masterId: Int get() = data["api_mst_id"].int()
    }

    inner class Stage1 {
        private val stage1: JsonElement get() = data["api_stage1"]

        val isAvailable: Boolean get() = stage1 !== jsonNull

        /**
         * 我方飞机数
         */
        val fCount: Int get() = stage1["api_f_count"].int()
        /**
         * 我方失去飞机数
         */
        val fLostCount: Int get() = stage1["api_f_lostcount"].int()
        /**
         * 敌方飞机数
         */
        val eCount: Int get() = stage1["api_e_count"].int()
        /**
         * 敌方失去飞机数
         */
        val eLostCount: Int get() = stage1["api_e_lostcount"].int()

        /**
         * 制空权Flag
         * 0=制空均衡, 1=制空権確保, 2=航空優勢, 3=航空劣勢, 4=制空権喪失
         */
        val airSuperiority: Int get() = stage1["api_disp_seiku"].int()

        /**
         * 我方接触飞机
         */
        val fTouchPlane: Int get() = stage1["api_touch_plane"][0].int()
        /**
         * 敌方接触飞机
         */
        val eTouchPlane: Int get() = stage1["api_touch_plane"][1].int()
    }

    inner class Stage2 {
        private val stage2: JsonElement get() = data["api_stage2"]

        val isAvailable: Boolean get() = stage2 !== jsonNull

        /**
         * 我方舰攻+舰爆+水爆+...总数
         */
        val fCount: Int get() = stage2["api_f_count"].int()
        /**
         * 我方被击落数
         */
        val fLostCount: Int get() = stage2["api_f_lostcount"].int()
        /**
         * 敌方舰攻+舰爆+水爆+...总数
         */
        val eCount: Int get() = stage2["api_e_count"].int()
        /**
         * 敌方被击落数
         */
        val eLostCount: Int get() = stage2["api_e_lostcount"].int()

        /**
         * 对空CI发动舰Index, 从0开始算起
         */
        val aaCutinShipIndex: Int get() = stage2["api_air_fire"]["api_idx"].int()
        /**
         * 对空CI类别
         * 1:高角砲x2/電探
         * 2:高角砲/電探
         * 3:高角砲x2
         * 4:大口径主砲/三式弾/高射装置/電探
         * 5:高角砲+高射装置x2/電探
         * 6:大口径主砲/三式弾/高射装置
         * 7:高角砲/高射装置/電探
         * 8:高角砲+高射装置/電探
         * 9:高角砲/高射装置
         * 10:高角砲/集中機銃/電探
         * 11:高角砲/集中機銃
         * 12:集中機銃/機銃/電探
         * 14:高角砲/対空機銃/電探
         * 15:高角砲/対空機銃
         * 16:高角砲/対空機銃/電探
         * 17:高角砲/対空機銃
         * 18:集中機銃
         */
        val aaCutinKind: Int get() = stage2["api_air_fire"]["api_kind"].int()
        /**
         * 对空CI装备
         */
        val aaCutinEquipments: List<Int> get() = stage2["api_air_fire"]["api_use_items"].list()
    }

    open inner class Stage3 {
        protected open val stage3: JsonElement get() = data["api_stage3"]

        val isAvailable: Boolean get() = stage3 !== jsonNull

        /**
         * 我方被舰攻雷击Flag
         */
        val fTorpedo: List<Int> get() = stage3["api_frai_flag"].list<Int>()
        /**
         * 我方被舰爆攻击Flag
         */
        val fBomb: List<Int> get() = stage3["api_fbak_flag"].list<Int>()
        /**
         * 我方被暴击Flag
         * 0=命中或Miss, 1=暴击
         */
        val fCritical: List<Int> get() = stage3["api_fcl_flag"].list<Int>()
        /**
         * 我方受到伤害
         */
        val fDamageReceive: List<Int> get() = stage3["api_fdam"].list<Int>()

        /**
         * 敌方被舰攻雷击Flag
         */
        val eTorpedo: List<Int> get() = stage3["api_erai_flag"].list<Int>()
        /**
         * 敌方被舰爆攻击Flag
         */
        val eBomb: List<Int> get() = stage3["api_ebak_flag"].list<Int>()
        /**
         * 敌方被暴击Flag
         * 0=命中或Miss, 1=暴击
         */
        val eCritical: List<Int> get() = stage3["api_ecl_flag"].list<Int>()
        /**
         * 敌方受到伤害
         */
        val eDamageReceive: List<Int> get() = stage3["api_edam"].list<Int>()
    }

    inner class Stage3Combined : Stage3() {
        override val stage3: JsonElement get() = data["api_stage3_combined"]
    }
}