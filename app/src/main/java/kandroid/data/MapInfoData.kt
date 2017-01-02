package kandroid.data

import kandroid.data.Start2Data.MasterMapInfoData
import kandroid.data.JsonWrapper
import kandroid.utils.Identifiable
import kandroid.utils.json.get
import kandroid.utils.json.int

class MapInfoData : ApiWrapper(), Identifiable {

    val mapID: Int get() = data["api_id"].int()

    val masterMapInfo: MasterMapInfoData get() = KCDatabase.master.masterMapInfoData[mapID]!!

    val mapAreaID: Int get() = masterMapInfo.mapAreaId

    val mapInfoID: Int get() = masterMapInfo.mapInfoId

    val name: String get() = masterMapInfo.name

    val difficulty: Int get() = masterMapInfo.difficulty

    val operationName: String get() = masterMapInfo.operationName

    val information: String get() = masterMapInfo.information

    val requiredDefeatedCount: Int get() = masterMapInfo.requiredDefeatedCount

    val isCleared: Boolean get() = data["api_cleared"].int() != 0

    val currentDefeatedCount: Int get() = data["api_defeat_count"].int()

    val mapHPCurrent: Int get() = data["api_eventmap"]["api_now_maphp"].int()

    val mapHPMax: Int get() = data["api_eventmap"]["api_max_maphp"].int()

    val eventDifficulty: Int get() = data["api_eventmap"]["api_selected_rank"].int()

    val gaugeType: Int get() = data["api_eventmap"]["api_gauge_type"].int()

    override val id: Int get() = mapID
}