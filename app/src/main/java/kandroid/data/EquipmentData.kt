package kandroid.data

import com.google.gson.JsonElement
import kandroid.data.Start2Data.MasterEquipmentData
import kandroid.data.JsonWrapper
import kandroid.observer.kcsapi.api_req_kousyou
import kandroid.utils.CatException
import kandroid.utils.Identifiable
import kandroid.utils.json.get
import kandroid.utils.json.int
import kandroid.utils.json.set

class EquipmentData : JsonWrapper(), Identifiable {

    /**
     * 装备固有id
     */
    val masterID: Int get() = data["api_id"].int()
    /**
     * master数据Id
     */
    val equipmentID: Int get() = data["api_slotitem_id"].int()
    /**
     * 锁了?
     */
    val isLocked: Boolean get() = data["api_locked"].int() != 0
    /**
     * 改修星数
     */
    val level: Int get() = data["api_level"].int()
    /**
     * 老司机熟练度
     */
    val aircraftLevel: Int get() = data["api_alv"].int()
    /**
     * master数据
     */
    val masterEquipment: MasterEquipmentData get() = KCDatabase.master.masterEquipmentData[masterID]!!

    val name: String get() = masterEquipment.name

    val nameWithLevel: String get() = if (level > 0) {
        if (aircraftLevel > 0) "$name+$level Lv. $aircraftLevel" else "$name+$level"
    } else {
        if (aircraftLevel > 0) "$name Lv. $aircraftLevel" else name
    }

    override val id: Int get() = masterID

    override fun toString(): String = nameWithLevel

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        // 由于GSON轮子的缘故, 只需要api_id和api_slotitem_id即可正常运作 TODO 复查
        super.loadFromResponse(apiName, responseData)
    }
}