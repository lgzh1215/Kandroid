package kandroid.newdata

import kandroid.utils.IDDictionary
import kandroid.utils.Identifiable
import kandroid.utils.json.get
import kandroid.utils.json.int
import kandroid.utils.json.list
import kandroid.utils.json.string

class Start2Data {

    val masterShipData = IDDictionary<MasterShipData>()
    val masterEquipmentData = IDDictionary<MasterEquipmentData>()
    val shipType = IDDictionary<ShipType>()
    val equipmentType = IDDictionary<EquipmentType>()
    val masterUseItemData = IDDictionary<MasterUseItemData>()
    val masterMapInfoData = IDDictionary<MasterMapInfoData>()
    val masterMissionData = IDDictionary<MasterMissionData>()

    class MasterShipData : JsonWrapper(), Identifiable {

        val shipId: Int get() = data["api_id"].int()
        val albumNo: Int get() = data["api_sortno"].int()
        val name: String get() = data["api_name"].string()
        val nameReading: String get() = data["api_yomi"].string()
        val shipType: Int get() = data["api_stype"].int()
        val remodelAfterLevel: Int get() = data["api_afterlv"].int()
        val remodelAfterShipId: Int get() = data["api_aftershipid"].int()
        val remodelAfterShip: MasterShipData? get() = KCDatabase.master.masterShipData[remodelAfterShipId]
        var remodelBeforeShipId: Int = 0
        val remodelBeforeShip: MasterShipData? get() = KCDatabase.master.masterShipData[remodelBeforeShipId]
        val remodelAmmo: Int get() = data["api_afterbull"].int()
        val remodelSteel: Int get() = data["api_afterfuel"].int()
        var needBlueprint: Int = 0
        var needCatapult: Int = 0
        //region Parameters
        val hpMin: Int get() = data["api_taik"][0].int()
        val hpMax: Int get() = data["api_taik"][1].int()
        val armorMin: Int get() = data["api_souk"][0].int()
        val armorMax: Int get() = data["api_souk"][1].int()
        val firepowerMin: Int get() = data["api_houg"][0].int()
        val firepowerMax: Int get() = data["api_houg"][1].int()
        val torpedoMin: Int get() = data["api_raig"][0].int()
        val torpedoMax: Int get() = data["api_raig"][1].int()
        val aaMin: Int get() = data["api_tyku"][0].int()
        val aaMax: Int get() = data["api_tyku"][1].int()
        val asw: Int get() = throw UnsupportedOperationException()
        val evasion: Int get() = throw UnsupportedOperationException()
        val los: Int get() = throw UnsupportedOperationException()
        val luckMin: Int get() = data["api_luck"][0].int()
        val luckMax: Int get() = data["api_luck"][1].int()
        val speed: Int get() = data["api_soku"].int(-1)
        val range: Int get() = data["api_leng"].int()
        //endregion
        val slotSize: Int get() = data["api_slot_num"].int()
        val aircraft: List<Int> get() = data["api_maxeq"].list()
        val aircraftTotal: Int get() = aircraft.sumBy { Math.max(it, 0) }
        val defaultSlot: List<Int> get() = throw UnsupportedOperationException()
        val buildingTime: Int get() = data["api_buildtime"].int()
        val material: List<Int> get() = data["api_broken"].list()
        val powerUp: List<Int> get() = data["api_powup"].list()
        val rarity: Int get() = data["api_backs"].int()
        val messageGet: String get() = data["api_getmes"].string().replace("<br>", "\n")
        val messageAlbum: String get() = throw UnsupportedOperationException()
        val fuel: Int get() = data["api_fuel_max"].int()
        val ammo: Int get() = data["api_fuel_max"].int()
        val voiceFlag: Int get() = data["api_fuel_max"].int()
        val hpMaxMarried: Int get() {
            val incr: Int
            val hpMin = hpMin
            when {
                hpMin < 30 -> incr = 4
                hpMin < 40 -> incr = 5
                hpMin < 50 -> incr = 6
                hpMin < 70 -> incr = 7
                hpMin < 90 -> incr = 8
                else -> incr = 9
            }
            return Math.min(hpMin + incr, hpMax)
        }
        val isAbyssalShip: Boolean get() = shipId in 501..900
        val abyssalShipClass: Int get() = throw UnsupportedOperationException()

        override val id: Int get() = shipId
    }

    class MasterEquipmentData : JsonWrapper(), Identifiable {

        val equipmentId: Int get() = data["api_id"].int()
        val albumNo: Int get() = data["api_sortno"].int()
        val name: String get() = data["api_name"].string()
        val equipmentType: List<Int> get() = data["api_type"].list()
        //region Parameters
        val armor: Int get() = data["api_souk"].int()
        val firepower: Int get() = data["api_houg"].int()
        val torpedo: Int get() = data["api_raig"].int()
        val bomber: Int get() = data["api_baku"].int()
        val aa: Int get() = data["api_tyku"].int()
        val asw: Int get() = data["api_tais"].int()
        val accuracy: Int get() = data["api_houm"].int()
        val evasion: Int get() = data["api_houk"].int()
        val lOS: Int get() = data["api_saku"].int()
        val luck: Int get() = data["api_luck"].int()
        val range: Int get() = data["api_leng"].int()
        //endregion
        val rarity: Int get() = data["api_rare"].int()
        val material: List<Int> get() = data["api_broken"].list()
        val message: String get() = data["api_info"].string().replace("<br>", "\n")
        val aircraftCost: Int get() = data["api_cost"].int()
        val aircraftDistance: Int get() = data["api_distance"].int()
        val isAbyssalEquipment: Boolean get() = equipmentId > 500
        val isListedInAlbum: Boolean get() = albumNo > 0
        val cardType: Int get() = data["api_type"].list<Int>()[1]
        val categoryType: Int get() = data["api_type"].list<Int>()[2]
        val categoryTypeInstance: EquipmentType get() = KCDatabase.master.equipmentType[categoryType]
        val iconType: Int get() = data["api_type"].list<Int>()[3]

        override val id: Int get() = equipmentId
    }

    class ShipType : JsonWrapper(), Identifiable {
        override val id: Int get() = throw UnsupportedOperationException()
    }

    class EquipmentType : JsonWrapper(), Identifiable {
        override val id: Int get() = throw UnsupportedOperationException()
    }

    class MasterUseItemData : JsonWrapper(), Identifiable {
        override val id: Int get() = throw UnsupportedOperationException()
    }

    class MasterMapInfoData : JsonWrapper(), Identifiable {
        override val id: Int get() = throw UnsupportedOperationException()
    }

    class MasterMissionData : JsonWrapper(), Identifiable {
        override val id: Int get() = throw UnsupportedOperationException()
    }
}