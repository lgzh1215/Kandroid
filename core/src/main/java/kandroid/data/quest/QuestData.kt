package kandroid.data.quest

import kandroid.data.JsonWrapper
import kandroid.data.RequestDataListener
import kandroid.observer.kcsapi.api_req_quest
import kandroid.utils.exception.CatException
import kandroid.utils.collection.Identifiable
import kandroid.utils.json.get
import kandroid.utils.json.int
import kandroid.utils.json.set
import kandroid.utils.json.string

class QuestData : JsonWrapper(), RequestDataListener, Identifiable {
    /**
     * 任務ID
     */
    val questId: Int get() = data["api_no"].int()
    /**
     * 1=編成, 2=出撃, 3=演習, 4=遠征, 5=補給/入渠, 6=工廠, 7=改装, 8=出撃(2), 9=その他?
     */
    val catagory: Int get() = data["api_category"].int()
    /**
     * 1=Daily, 2=Weekly, 3=Monthly, 4=単発, 5=他
     */
    val type: Int get() = data["api_type"].int()
    /**
     * 遂行状態
     * 1=未受領, 2=遂行中, 3=達成
     */
    val state: Int get() = data["api_state"].int()
    /**
     * 任務名
     */
    val name: String get() = data["api_title"].string()
    /**
     * 説明
     */
    val detail: String get() = data["api_detail"].string().replace("<br>", "\r\n")
    /**
     * 1=通常, 2=艦娘
     */
    val bonusFlag: Int get() = data["api_bonus_flag"].int()
    /**
     * 進捗, 0=空白(達成含む), 1=50%以上達成, 2=80%以上達成
     */
    val progressFlag: Int get() = data["api_progress_flag"].int()

    override val id: Int get() = questId

    override fun loadFromRequest(apiName: String, requestData: MutableMap<String, String>) {
        when (apiName) {
            api_req_quest.stop.name -> {
                data["api_state"] = 1
            }
            else -> throw CatException()
        }
    }
}