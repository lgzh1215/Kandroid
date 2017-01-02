package kandroid.data.battle

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kandroid.data.JsonWrapper
import kandroid.data.KCDatabase
import kandroid.data.MapInfoData
import kandroid.utils.json.*

class CompassData : JsonWrapper() {

    /**
     * 海域id (例:2-3的2)
     */
    val mapAreaID: Int get() = data["api_maparea_id"].int()
    /**
     * 海域id2 (例:2-3的3)
     */
    val mapInfoID: Int get() = data["api_mapinfo_no"].int()
    /**
     * Next Cell
     */
    val destination: Int get() = data["api_no"].int()
    /**
     * Next Cell's Color
     */
    val colorID: Int get() = data["api_color_no"].int()
    /**
     * 0=初期位置, 2=資源, 3=渦潮, 4=通常戦闘, 5=Boss点, 6=无战斗(错觉点), 7=航空戦, 8=船団護衛成功
     */
    val eventID: Int get() = data["api_event_id"].int()
    /**
     * 0=非戦闘, 1=通常戦闘, 2=夜戦, 3=夜昼戦, 4=航空戦　
     * api_event_id=6(无战斗(错觉点))時は 0="気のせいだった。", 1="敵影を見ず。", 2=能動分岐
     * api_event_id=7(航空戦)時は　0=航空偵察, 4=航空戦
     */
    val eventKind: Int get() = data["api_event_kind"].int()
    /**
     * 路线分歧数
     */
    val nextBranchCount: Int get() = data["api_next"].int()
    /**
     * 是否终点
     */
    val isEndPoint: Boolean get() = nextBranchCount == 0
    /**
     * 气泡信息
     * 0=なし 1=<敵艦隊発見!> 2=<攻撃目標発見!>? 3=<針路哨戒!>?
     */
    val commentID: Int get() = data["api_comment_kind"].int()
    /**
     * 索敌成功了么 0=失敗, 1=成功
     */
    val launchedRecon: Int get() = data["api_production_kind"].int()
    /**
     * 资源点
     */
    fun getItems(): List<GetItemData> {
        var json: JsonElement = data["api_itemget"]
        if (json === jsonNull) {
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
    /**
     * 旋涡点失去的Item的id
     * 1=燃料, 2=弾薬
     */
    val whirlpoolItemID: Int get() = data["api_happening"]["api_mst_id"].int(-1)
    /**
     * 旋涡点失去的Item的数量
     */
    val whirlpoolItemAmount: Int get() = data["api_happening"]["api_count"].int()
    /**
     * 是否电探减少旋涡点损失
     */
    val isWhirlpoolRadarFlag: Boolean get() = data["api_happening"]["api_dentan"].int() != 0
    /**
     * 能动分歧可以选择的点
     */
    val routeChoices: List<Int> get() = data["api_select_route"]["api_select_cells"].list()
    /**
     * 航空侦查点的航空机(例:K作战6-3)
     * 0=无, 1=大型飛行艇, 2=水上偵察機
     */
    val airReconnaissancePlane: Int get() = data["api_airsearch"]["api_plane_type"].int()
    /**
     * 航空侦查结果
     * 0=失敗, 1=成功, 2=大成功
     */
    val airReconnaissanceResult: Int get() = data["api_airsearch"]["api_result"].int()
    /**
     * 次奥,被泡芙抄家了
     */
    val isHasAirRaid: Boolean get() = airRaidData != null
    /**
     * 抄家情报数据
     */
    val airRaidData: JsonElement? get() = data["api_destruction_battle"].obj
    /**
     * 抄家空襲被害的类别
     * 1=資源に被害, 2=資源・航空隊に被害, 3=航空隊に被害, 4=損害なし
     */
    val airRaidDamageKind: Int get() = data["api_destruction_battle"]["api_lost_kind"].int()
    /**
     * 海域情报
     */
    val mapInfo: MapInfoData? get() = KCDatabase.mapInfo.get(mapAreaID * 10 + mapInfoID)

    data class GetItemData(var ItemID: Int, var Metadata: Int, var Amount: Int)
}
