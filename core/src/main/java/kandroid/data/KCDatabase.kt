package kandroid.data

import kandroid.config.Config
import kandroid.utils.IDDictionary
import kandroid.utils.Utils
import org.apache.commons.io.FileUtils
import java.nio.charset.Charset
import kotlin.printStackTrace

object KCDatabase {

    val master: Start2Data by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        println("loadMaster")
        var start2Data: Start2Data? = null
        try {
            val file = Config.config.getSaveDataFile("master")
            val data = org.apache.commons.io.FileUtils.readFileToString(file, Charset.defaultCharset())
            start2Data = Utils.GSON.fromJson(data, Start2Data::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (start2Data != null)
            start2Data
        else
            Start2Data()
    }

    private var _player: Player = Player()
    private val player: Player
        get() = _player

    fun saveMaster() {
        val master = master
        if (!master.isInited) return
        try {
            val file = Config.config.getSaveDataFile("master")
            val json = Utils.GSON.toJson(master)
            org.apache.commons.io.FileUtils.writeStringToFile(file, json, Charset.defaultCharset())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    val equipments: IDDictionary<EquipmentData>
        get() = player.equipments

    val arsenalData: IDDictionary<ArsenalData>
        get() = player.arsenalData

    val useItems: IDDictionary<UseItem>
        get() = player.items

    val material: MaterialData
        get() = player.material

    val admiral: AdmiralData
        get() = player.admiral

    val ships: IDDictionary<ShipData>
        get() = player.ships

    val dockData: IDDictionary<DockData>
        get() = player.dockData

    val fleets: FleetManager
        get() = player.fleets

    val mapInfoDatas: IDDictionary<MapInfoData>
        get() = player.mapInfoDatas
}

data class Player(
        var ships: IDDictionary<ShipData> = IDDictionary<ShipData>(),
        var equipments: IDDictionary<EquipmentData> = IDDictionary<EquipmentData>(),
        var items: IDDictionary<UseItem> = IDDictionary<UseItem>(),

        var arsenalData: IDDictionary<ArsenalData> = IDDictionary<ArsenalData>(),
        var material: MaterialData = MaterialData(),
        var admiral: AdmiralData = AdmiralData(),
        var dockData: IDDictionary<DockData> = IDDictionary<DockData>(),

        var fleets: FleetManager = FleetManager(),

        var mapInfoDatas: IDDictionary<MapInfoData> = IDDictionary<MapInfoData>()
) {}