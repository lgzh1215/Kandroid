package kandroid.data

import com.google.gson.JsonElement
import kandroid.data.Start2Data.MasterShipData
import kandroid.observer.kcsapi.api_get_member
import kandroid.observer.kcsapi.api_port
import kandroid.observer.kcsapi.api_req_kaisou
import kandroid.utils.CatException
import kandroid.utils.Identifiable
import kandroid.utils.json.get
import kandroid.utils.json.int
import kandroid.utils.json.list
import kandroid.utils.json.set

class ShipData : JsonWrapper(), RequestDataListener, Identifiable {

    val masterID: Int get() = data["api_id"].int()
    val sortID: Int get() = data["api_sortno"].int()
    val shipID: Int get() = data["api_ship_id"].int()
    val level: Int get() = data["api_lv"].int()
    val expTotal: Int get() = data["api_exp"][0].int()
    val expNext: Int get() = data["api_exp"][1].int()
    val hpCurrent: Int get() = data["api_nowhp"].int()
    val hpMax: Int get() = data["api_maxhp"].int()
    val range: Int get() = data["api_leng"].int()
    val slot: List<Int> get() = data["api_slot"].list()
    val slotMaster: IntArray get() = throw UnsupportedOperationException()
    val slotInstance: Array<EquipmentData> get() = throw UnsupportedOperationException()
    val slotInstanceMaster: Array<Start2Data.MasterEquipmentData> get() = throw UnsupportedOperationException()
    val expansionSlot: Int get() = data["api_slot_ex"].int()
    val expansionSlotMaster: Int get() = throw UnsupportedOperationException()
    val expansionSlotInstance: EquipmentData get() = throw UnsupportedOperationException()
    val expansionSlotInstanceMaster: Start2Data.MasterEquipmentData get() = throw UnsupportedOperationException()
    val allSlot: IntArray get() = throw UnsupportedOperationException()
    val allSlotMaster: IntArray get() = throw UnsupportedOperationException()
    val allSlotInstance: Array<EquipmentData> get() = throw UnsupportedOperationException()
    val allSlotInstanceMaster: Array<Start2Data.MasterEquipmentData> get() = throw UnsupportedOperationException()
    val aircraft: List<Int> get() = data["api_onslot"].list()
    val aircraftTotal: Int get() = aircraft.sumBy { Math.max(it, 0) }
    val fuel: Int get() = data["api_fuel"].int()
    val ammo: Int get() = data["api_bull"].int()
    val slotSize: Int get() = data["api_slotnum"].int()
    val repairTime: Int get() = data["api_ndock_time"].int()
    val repairSteel: Int get() = data["api_ndock_item"][1].int()
    val repairFuel: Int get() = data["api_ndock_item"][0].int()
    val condition: Int get() = data["api_cond"].int()
    //region Parameters
    val firepowerModernized: Int get() = data["api_kyouka"][0].int()
    val torpedoModernized: Int get() = data["api_kyouka"][1].int()
    val aaModernized: Int get() = data["api_kyouka"][2].int()
    val armorModernized: Int get() = data["api_kyouka"][3].int()
    val luckModernized: Int get() = data["api_kyouka"][4].int()
    val firepowerRemain: Int get() {
        val master = masterShip
        return master.firepowerMax - master.firepowerMin - firepowerModernized
    }
    val torpedoRemain: Int get() {
        val master = masterShip
        return master.torpedoMax - master.torpedoMin - torpedoModernized
    }
    val aaRemain: Int get() {
        val master = masterShip
        return master.aaMax - master.aaMin - aaModernized
    }
    val armorRemain: Int get() {
        val master = masterShip
        return master.armorMax - master.armorMin - armorModernized
    }
    val luckRemain: Int get() {
        val master = masterShip
        return master.luckMax - master.luckMin - luckModernized
    }
    val firepowerTotal: Int get() = data["api_karyoku"][0].int()
    val torpedoTotal: Int get() = data["api_raisou"][0].int()
    val aaTotal: Int get() = data["api_taiku"][0].int()
    val armorTotal: Int get() = data["api_soukou"][0].int()
    val evasionTotal: Int get() = data["api_kaihi"][0].int()
    val aswTotal: Int get() = data["api_taisen"][0].int()
    val losTotal: Int get() = data["api_sakuteki"][0].int()
    val luckTotal: Int get() = data["api_lucky"][0].int()
    val bomberTotal: Int get() = throw UnsupportedOperationException()
    val firepowerBase: Int get() = masterShip.firepowerMin + firepowerModernized
    val torpedoBase: Int get() = masterShip.torpedoMin + torpedoModernized
    val aaBase: Int get() = masterShip.aaMin + aaModernized
    val armorBase: Int get() = masterShip.armorMin + armorModernized
    val evasionBase: Int get() = throw UnsupportedOperationException()
    val aswBase: Int get() = throw UnsupportedOperationException()
    val losBase: Int get() = throw UnsupportedOperationException()
    val luckBase: Int get() = masterShip.luckMin + luckModernized
    val evasionMax: Int get() = data["api_kaihi"][1].int()
    val aswMax: Int get() = data["api_taisen"][1].int()
    val losMax: Int get() = data["api_sakuteki"][1].int()
    //endregion
    val bombTotal: Int get() = throw UnsupportedOperationException()
    val isLocked: Boolean get() = data["api_locked"].int() != 0
    val isLockedByEquipment: Boolean get() = data["api_locked_equip"].int() != 0
    val sallyArea: Int get() = data["api_sally_area"].int(-1)
    val masterShip: MasterShipData get() = KCDatabase.master.masterShipData.get(shipID)
    val repairingDockID: Int get() = throw UnsupportedOperationException()
    val fleet: Int get() = throw UnsupportedOperationException()
    val fleetWithIndex: String get() = throw UnsupportedOperationException()
    val isMarried: Boolean get() = level > 99
    val expNextRemodel: Int get() = throw UnsupportedOperationException()
    val name: String get() = masterShip.name
    val nameWithLevel: String get() = throw UnsupportedOperationException()
    val hpRate: Double get() = throw UnsupportedOperationException()
    val fuelMax: Int get() = masterShip.fuel
    val ammoMax: Int get() = masterShip.ammo
    val fuelRate: Double get() = throw UnsupportedOperationException()
    val ammoRate: Double get() = throw UnsupportedOperationException()
    val aircraftRate: List<Double> get() = throw UnsupportedOperationException()
    val aircraftTotalRate: Double get() = throw UnsupportedOperationException()
    val isExpansionSlotAvailable: Boolean get() = expansionSlot != 0

    override val id: Int get() = masterID

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        when (apiName) {
            api_port.port.name,
            api_get_member.ship2.name,
            api_get_member.ship3.name,
            api_get_member.ship_deck.name -> {
                super.loadFromResponse(apiName, responseData)
            }
            else -> throw CatException()
        }
    }

    override fun loadFromRequest(apiName: String, requestData: Map<String, String>) {
        when (apiName) {
            api_req_kaisou.open_exslot.name -> {
                data["api_slot_ex"] = -1
            }
            else -> throw CatException()
        }
    }

    fun repair() {
        data["api_nowhp"] = hpMax
        data["api_cond"] = Math.max(condition, 40)

        data["api_ndock_time"] = 0
        data["api_ndock_item"][0] = 0
        data["api_ndock_item"][1] = 0
    }
}
