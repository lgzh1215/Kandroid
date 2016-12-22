package kandroid.data

import kandroid.data.JsonWrapper
import kandroid.data.RequestDataListener
import kandroid.utils.Identifiable
import kandroid.utils.json.*
import java.util.*

class AdmiralData : JsonWrapper(), RequestDataListener, Identifiable {

    val admiralId: Int get() = data["api_member_id"].int()
    val admiralName: String get() = data["api_nickname"].string()
    val startTime: Date get() = Date(data["api_starttime"].long())
    val level: Int get() = data["api_level"].int()
    val rank: Int get() = data["api_rank"].int()
    val exp: Int get() = data["api_experience"].int()
    val comment: String get() = data["api_comment"].string()
    val maxShipCount: Int get() = data["api_max_chara"].int()
    val maxEquipmentCount: Int get() = data["api_max_slotitem"].int()
    val fleetCount: Int get() = data["api_count_deck"].int()
    val arsenalCount: Int get() = data["api_count_kdock"].int()
    val dockCount: Int get() = data["api_count_ndock"].int()
    val furnitureCoin: Int get() = data["api_fcoin"].int()
    val sortieWin: Int get() = data["api_st_win"].int()
    val sortieLose: Int get() = data["api_st_lose"].int()
    val missionCount: Int get() = data["api_ms_count"].int()
    val missionSuccess: Int get() = data["api_ms_success"].int()
    val practiceWin: Int get() = data["api_pt_win"].int()
    val practiceLose: Int get() = data["api_pt_lose"].int()
    val maxResourceRegenerationAmount: Int get() = level * 250 + 750

    override val id: Int get() = admiralId

    override fun loadFromRequest(apiName: String, requestData: Map<String, String>) {
        if (apiName == "api_req_member/updatecomment") {
            val comment = requestData["api_cmt"]
            if (comment != null) data["api_comment"] = comment
        }
    }
}

