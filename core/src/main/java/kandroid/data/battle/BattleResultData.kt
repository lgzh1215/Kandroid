package kandroid.data.battle

import kandroid.data.JsonWrapper
import kandroid.utils.json.get
import kandroid.utils.json.int
import kandroid.utils.json.jsonNull
import kandroid.utils.json.string

class BattleResultData : JsonWrapper() {
    val rank: String get() = data["api_win_rank"].string()

    val mvpIndex: Int get() = data["api_mvp"].int()

    val mVPIndexCombined: Int get() = data["api_mvp_combined"].int()

    val admiralExp: Int get() = data["api_get_exp"].int()

    val shipExp: Int get() = data["api_get_base_exp"].int()

    val enemyFleetName: String get() = data["api_enemy_info"]["api_deck_name"].string()

    val droppedShipID: Int get() = data["api_get_ship"]["api_ship_id"].int()

    val droppedItemID: Int get() = data["api_get_useitem"]["api_useitem_id"].int()

    val droppedEquipmentID: Int get() = data["api_get_slotitem"]["api_slotitem_id"].int()

    val canEscape: Boolean get() = data["api_escape"] != jsonNull

    val escapingShipIndex:Int get() = data["api_escape"]["api_escape_idx"].int()
}