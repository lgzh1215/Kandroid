package kandroid.data

import kandroid.data.Start2Data.MasterEquipmentData
import kandroid.data.JsonWrapper
import kandroid.utils.Identifiable
import kandroid.utils.json.get
import kandroid.utils.json.int

class EquipmentData : JsonWrapper(), Identifiable {

    val masterID: Int get() = data["api_id"].int()

    val equipmentID: Int get() = data["api_slotitem_id"].int()

    val isLocked: Boolean get() = data["api_locked"].int() != 0

    val level: Int get() = data["api_level"].int()

    val aircraftLevel: Int get() = data["api_alv"].int()

    val masterEquipment: MasterEquipmentData get() = KCDatabase.master.masterEquipmentData[masterID]

    val name: String get() = masterEquipment.name

    val nameWithLevel: String get() = if (level > 0) {
        if (aircraftLevel > 0) "$name+$level Lv. $aircraftLevel" else "$name+$level"
    } else {
        if (aircraftLevel > 0) "$name Lv. $aircraftLevel" else name
    }

    override val id: Int get() = masterID

    override fun toString(): String = nameWithLevel
}