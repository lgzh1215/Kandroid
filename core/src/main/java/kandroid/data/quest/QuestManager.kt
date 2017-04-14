package kandroid.data.quest

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kandroid.data.RequestDataListener
import kandroid.data.ResponseDataListener
import kandroid.observer.kcsapi.api_get_member
import kandroid.observer.kcsapi.api_req_quest
import kandroid.utils.exception.CatException
import kandroid.utils.collection.IDDictionary
import kandroid.utils.json.array
import kandroid.utils.json.get
import kandroid.utils.json.int

class QuestManager : RequestDataListener, ResponseDataListener {

    /**
     * 已知的任务
     */
    val questDatas = IDDictionary<QuestData>()
    /**
     * 总任务数
     */
    var count = 0
    var isLoaded = false
    /**
     * 任务列表左侧任务筛选tab
     * 0=全All, 1=日常, 2=周常, 3=月常, 4=一次性任务, 5=他Others, 9=进行中任务
     */
    var tabId = 0

    fun clear() {
        questDatas.clear()
        isLoaded = false
    }

    override fun loadFromRequest(apiName: String, requestData: MutableMap<String, String>) {
        when (apiName) {
            api_get_member.questlist.name -> {
                tabId = requestData["api_tab_id", tabId] // Done
            }
            api_req_quest.clearitemget.name -> {
                val questId = requestData["api_quest_id", 0]
                questDatas.remove(questId)
                count--
            }
            api_req_quest.stop.name -> {
                val questId = requestData["api_quest_id", 0]
                questDatas[questId]?.loadFromRequest(apiName, requestData)
            }
            else -> CatException()
        }
    }

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        when (apiName) {
            api_get_member.questlist.name -> {
                // TODO mission progress

                if (tabId == 0) { // 必须是全部任务
                    count = responseData["api_count"].int()
                }

                val questList = responseData["api_list"].array
                if (questList != null) {
                    for (elem in questList) {
                        if (elem is JsonObject) { //
                            val questId = elem["api_no"].int()
                            var quest = questDatas[questId]
                            if (quest == null) {
                                quest = QuestData()
                                quest.loadFromResponse(apiName, elem)
                            } else {
                                quest.loadFromResponse(apiName, elem)
                            }
                        }
                    }
                }

                isLoaded = true
            }
            else -> CatException()
        }
    }
}