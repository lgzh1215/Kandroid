package kandroid.data.battle.phase

import com.google.gson.JsonElement
import kandroid.data.battle.BattleBase
import kandroid.utils.json.*
import java.util.*

class PhaseAirBattle(battle: BattleBase, val type: Type) : BasePhase(battle) {

    enum class Type {
        JetBase {
            override val jsonKey: String get() = "api_air_base_injection"
        },
        Jet {
            override val jsonKey: String get() = "api_injection_kouku"
        },
        NormalBase {
            override val jsonKey: String get() = "api_air_base_attack"
        },
        Normal {
            override val jsonKey: String get() = "api_kouku"
        };

        abstract val jsonKey: String
    }

    val airBattles: ArrayList<AirBattle> = ArrayList()

    init {
        when (type) {
            Type.NormalBase -> {
                data[type.jsonKey].array?.mapTo(airBattles) { AirBattle(it) }
            }
            Type.Normal -> {
                airBattles.add(AirBattle(data[type.jsonKey]))
                // 如果有第二波航空战
                if (data["api_stage_flag2"] !== jsonNull) {
                    airBattles.add(AirBattle(data["api_kouku2"], "api_stage_flag2"))
                }
            }
            else -> {
                airBattles.add(AirBattle(data[type.jsonKey]))
            }
        }
    }

    inner class AirBattle(val data: JsonElement, val stageFlagKey: String = "api_stage_flag") {

        val stateFlag: List<Int> get() {
            if (type == Type.NormalBase) {
                return data[stageFlagKey].list()
            } else {
                return this@PhaseAirBattle.data[stageFlagKey].list()
            }
        }

        val fPlaneFrom: List<Int> get() = data["api_plane_from"][0].list()
        val ePlaneFrom: List<Int> get() = data["api_plane_from"][1].list()

//        val planes: List<BaseAirCorpsPlaneData> get() {
//            if (type == Type.NormalBase) {
//
//            }
//        } TODO

        val s1 = Stage1()
        val s2 = Stage2()
        val s3 = Stage3()
        val s3c = Stage3Combined()

//        inner class BaseAirCorpsPlaneData(val index: Int) {
//            val count: Int get() = data[""]
//            val masterId: Int
//        }



        inner class Stage1 {
            val data: JsonElement get() = this@AirBattle.data["api_stage1"]

            val fCount: Int get() = data["api_f_count"].int()
            val fLostCount: Int get() = data["api_f_lostcount"].int()
            val eCount: Int get() = data["api_e_count"].int()
            val eLostCount: Int get() = data["api_e_lostcount"].int()

            val airSuperiority: Int get() = data["api_disp_seiku"].int()

            val fTouchPlane: Int get() = data["api_touch_plane"][0].int()
            val eTouchPlane: Int get() = data["api_touch_plane"][1].int()
        }

        inner class Stage2 {
            val data: JsonElement get() = this@AirBattle.data["api_stage2"]

            val fCount: Int get() = data["api_f_count"].int()
            val fLostCount: Int get() = data["api_f_lostcount"].int()
            val eCount: Int get() = data["api_e_count"].int()
            val eLostCount: Int get() = data["api_e_lostcount"].int()

            val aaCutinShipIndex: Int get() = data["api_air_fire"]["api_idx"].int()
            val aaCutinKind: Int get() = data["api_air_fire"]["api_kind"].int()
            val aaCutinEquipments: List<Int> get() = data["api_air_fire"]["api_use_items"].list()
        }

        open inner class Stage3 {
            open val data: JsonElement get() = this@AirBattle.data["api_stage3"]

            val fTorpedoFlag:List<Int> get() = data["api_frai_flag"].list<Int>().drop(1)
            val fBombFlag:List<Int> get() = data["api_fbak_flag"].list<Int>().drop(1)
            val fClFlag: List<Int> get() = data["api_fcl_flag"].list<Int>().drop(1)
            val fDamage: List<Int> get() = data["api_fdam"].list<Int>().drop(1)

            val eTorpedoFlag:List<Int> get() = data["api_erai_flag"].list<Int>().drop(1)
            val eBombFlag:List<Int> get() = data["api_ebak_flag"].list<Int>().drop(1)
            val eClFlag: List<Int> get() = data["api_ecl_flag"].list<Int>().drop(1)
            val eDamage: List<Int> get() = data["api_edam"].list<Int>().drop(1)
        }

        inner class Stage3Combined : Stage3() {
            override val data: JsonElement get() = this@AirBattle.data["api_stage3_combined"]
        }
    }
}