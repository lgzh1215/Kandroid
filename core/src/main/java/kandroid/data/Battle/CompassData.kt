package kandroid.data.battle

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kandroid.data.JsonWrapper
import kandroid.data.KCDatabase
import kandroid.data.MapInfoData
import kandroid.utils.json.get
import kandroid.utils.json.int
import kandroid.utils.json.list

class CompassData : JsonWrapper() {

    val mapAreaID: Int get() = data["api_maparea_id"].int()

    val mapInfoID: Int get() = data["api_mapinfo_no"].int()

    val destination: Int get() = data["api_no"].int()

    val colorID: Int get() = data["api_color_no"].int()

    val eventID: Int get() = data["api_event_id"].int()

    val eventKind: Int get() = data["api_event_kind"].int()

    val nextBranchCount: Int get() = data["api_next"].int()

    val isEndPoint: Boolean get() = nextBranchCount == 0

    val commentID: Int get() = data["api_comment_kind"].int()

    val launchedRecon: Int get() = data["api_production_kind"].int()

    data class GetItemData(var ItemID: Int, var Metadata: Int, var Amount: Int)

    fun GetItems(): List<GetItemData> {
        var json: JsonElement? = data["api_itemget"]
        if (json == null) {
            json = data["api_itemget_eo_comment"]
        }

        return when (json) {
            is JsonObject -> listOf(GetItemData(json["api_usemst"].int(),
                    json["api_id"].int(),
                    json["api_getcount"].int()))
            is JsonArray -> json.mapNotNull {
                GetItemData(it["api_usemst"].int(),
                        it["api_id"].int(),
                        it["api_getcount"].int())
            }
            else -> emptyList()
        }
    }

    val whirlpoolItemID: Int get() = data["api_happening"]["api_mst_id"].int(-1)

    val whirlpoolItemAmount: Int get() = data["api_happening"]["api_count"].int()

    val isWhirlpoolRadarFlag: Boolean get() = data["api_happening"]["api_dentan"].int() != 0

    val routeChoices: List<Int> get() = data["api_select_route"]["api_select_cells"].list()

    val airReconnaissancePlane: Int get() = data["api_airsearch"]["api_plane_type"].int()

    val airReconnaissanceResult: Int get() = data["api_airsearch"]["api_result"].int()

    val isHasAirRaid: Boolean get() = data["api_destruction_battle"] != null

    val airRaidDamageKind: Int get() = data["api_destruction_battle"]["api_lost_kind"].int()

    val mapInfo: MapInfoData get() = KCDatabase.mapInfo.get(mapAreaID * 10 + mapInfoID)
}
