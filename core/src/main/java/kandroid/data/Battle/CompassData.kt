package kandroid.data.Battle

import kandroid.data.Data
import kandroid.data.KCDatabase
import kandroid.data.MapInfoData
import kandroid.observer.POJO
import kandroid.observer.RawData

class CompassData : Data<CompassData.ApiCompass>() {

    val mapAreaID: Int
        get() = data.api_maparea_id

    val mapInfoID: Int
        get() = data.api_mapinfo_no

    val destination: Int
        get() = data.api_no

    val colorID: Int
        get() = data.api_color_no

    val eventID: Int
        get() = data.api_event_id

    val eventKind: Int
        get() = data.api_event_kind

    val nextBranchCount: Int
        get() = data.api_next

    val isEndPoint: Boolean
        get() = nextBranchCount == 0

    val commentID: Int
        get() = data.api_comment_kind

    val launchedRecon: Int
        get() = data.api_production_kind

    //region Description
    inner class GetItemData(var ItemID: Int, var Metadata: Int, var Amount: Int)

    @Deprecated("")
    fun GetItems(): Array<GetItemData> {
        throw UnsupportedOperationException()
    }
    //endregion

    val whirlpoolItemID: Int
        get() = if (data.api_happening == null) -1 else data.api_happening!!.api_mst_id

    val whirlpoolItemAmount: Int
        get() = if (data.api_happening == null) 0 else data.api_happening!!.api_count

    val isWhirlpoolRadarFlag: Boolean
        get() = data.api_happening != null && data.api_happening!!.api_dentan != 0

    val routeChoices: List<Int>?
        get() = if (data.api_select_route == null) null else data.api_select_route!!.api_select_cells

    val airReconnaissancePlane: Int
        get() = if (data.api_airsearch == null) 0 else data.api_airsearch!!.api_plane_type

    val airReconnaissanceResult: Int
        get() = if (data.api_airsearch == null) 0 else data.api_airsearch!!.api_result

    val isHasAirRaid: Boolean
        get() = data.api_destruction_battle != null

    val airRaidDamageKind: Int
        get() {
            if (isHasAirRaid)
                return data.api_destruction_battle!!.api_lost_kind
            else
                return 0
        }

    val mapInfo: MapInfoData
        get() = KCDatabase.mapInfoDatas.get(mapAreaID * 10 + mapInfoID)

    fun loadFromResponse(apiName: String, rawData: RawData) {
        // TODO
    }

    class ApiCompass : POJO() {
        var api_rashin_flg: Int = 0
        var api_rashin_id: Int = 0
        var api_maparea_id: Int = 0
        var api_mapinfo_no: Int = 0
        var api_no: Int = 0
        var api_color_no: Int = 0
        var api_event_id: Int = 0
        var api_event_kind: Int = 0
        var api_next: Int = 0
        var api_bosscell_no: Int = 0
        var api_bosscomp: Int = 0
        var api_comment_kind: Int = 0
        var api_production_kind: Int = 0
        var api_airsearch: ApiAirsearch? = null
        var api_happening: ApiHappening? = null
        var api_select_route: ApiSelectRoute? = null
        var api_destruction_battle: ApiDestructionBattle? = null

        class ApiAirsearch {
            var api_plane_type: Int = 0
            var api_result: Int = 0
        }

        class ApiHappening {
            var api_mst_id: Int = 0
            var api_count: Int = 0
            var api_dentan: Int = 0
        }

        class ApiSelectRoute {
            var api_select_cells: List<Int>? = null
        }

        class ApiDestructionBattle {

            var api_lost_kind: Int = 0
        }
    }
}
