package kandroid.data

import com.google.gson.JsonElement
import kandroid.observer.kcsapi.api_get_member
import kandroid.observer.kcsapi.api_req_quest
import kandroid.utils.CatException

class QuestManager : RequestDataListener, ResponseDataListener {

    override fun loadFromRequest(apiName: String, requestData: MutableMap<String, String>) {
        when (apiName) {
            api_req_quest.clearitemget.name -> {

            }
            api_req_quest.stop.name -> {

            }
            else -> CatException()
        }
        // TODO
    }

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        when (apiName) {
            api_get_member.questlist.name -> {

            }
            else -> CatException()
        }
        // TODO
    }
}