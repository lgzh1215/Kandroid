package kandroid.data

import kandroid.data.battle.BattleManager
import kandroid.data.quest.QuestManager
import kandroid.utils.collection.IDDictionary
import kandroid.utils.collection.Identifiable
import kotlin.coroutines.experimental.buildIterator

object KCDatabase {

    object Master {
        val ship = IDDictionary<MasterShipData>()
        val equipment = IDDictionary<MasterEquipmentData>()

        val shipType = IDDictionary<ShipType>()
        val equipmentType = IDDictionary<EquipmentType>()

        val useItem = IDDictionary<MasterUseItemData>()
        val mapArea = IDDictionary<MasterMapAreaData>()
        val mapInfo = IDDictionary<MasterMapInfoData>()
        val mission = IDDictionary<MasterMissionData>()
    }

    object User {
        private var _current: UserData = UserData.TEMP

        val current: UserData get() = _current

        val all = IDDictionary<UserData>().apply { put(UserData.TEMP) }
    }

    // delegate to current user
    val admiral: AdmiralData get() = User.current.admiral
    val ships: IDDictionary<ShipData> get() = User.current.ships
    val equipments: IDDictionary<EquipmentData> get() = User.current.equipments
    val material: MaterialData get() = User.current.material
    val useItems: IDDictionary<UseItem> get() = User.current.useItems
    val arsenals: IDDictionary<ArsenalData> get() = User.current.arsenals
    val docks: IDDictionary<DockData> get() = User.current.docks
    val fleets: FleetManager get() = User.current.fleets
    val mapInfo: IDDictionary<MapInfoData> get() = User.current.mapInfo
    val baseAirCorps: IDDictionary<BaseAirCorpsData> get() = User.current.baseAirCorps
    val quests: QuestManager get() = User.current.quests
    val battle: BattleManager get() = User.current.battle
}

class UserData(override val id: Int) : Identifiable {

    val admiral = AdmiralData()

    val ships = IDDictionary<ShipData>()
    val equipments = IDDictionary<EquipmentData>()

    val material = MaterialData()
    val useItems = IDDictionary<UseItem>()

    val arsenals = IDDictionary<ArsenalData>()
    /**
     * 入渠数据：id=1、2、3、4
     */
    val docks = IDDictionary<DockData>()

    val fleets = FleetManager()
    val mapInfo = IDDictionary<MapInfoData>()
    val baseAirCorps = IDDictionary<BaseAirCorpsData>()
    val quests = QuestManager()
    val battle = BattleManager()

    val some get() = buildIterator {
        yield(admiral)
        yield(ships)
        yield(equipments)
        yield(material)
        yield(useItems)
        yield(arsenals)
        yield(docks)
        yield(fleets)
        yield(mapInfo)
        yield(baseAirCorps)
        yield(quests)
        yield(battle)
    }

    companion object {
        val TEMP = UserData(id = 0)
    }
}