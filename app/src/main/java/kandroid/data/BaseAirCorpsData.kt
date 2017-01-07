package kandroid.data

import com.google.gson.JsonElement
import kandroid.observer.kcsapi.api_get_member
import kandroid.observer.kcsapi.api_req_air_corps
import kandroid.utils.CatException
import kandroid.utils.IDDictionary
import kandroid.utils.Identifiable
import kandroid.utils.json.*

class BaseAirCorpsData : JsonWrapper(), RequestDataListener, Identifiable {

    /**
     * 航空队id
     */
    val airCorpId: Int get() = data["api_rid"].int()
    /**
     * 陆基航空队部署的海域id
     */
    val mapAreaId: Int get() = data["api_area_id"].int()
    /**
     * 航空队名
     */
    val name: String get() = data["api_name"].string()
    /**
     * 戦闘行動半径
     */
    val distance: Int get() = data["api_distance"].int()
    /**
     * 行動指示
     * 0=待機, 1=出撃, 2=防空, 3=退避, 4=休息
     */
    val actionKind: Int get() = data["api_action_kind"].int()
    /**
     * 每队的飞机情报 id=0/1/2/3
     */
    val planeInfo = IDDictionary<PlaneData>()

    override val id: Int get() = getId(data!!)

    inner class PlaneData(override val id: Int) : Identifiable {
        /**
         * 第几格飞机
         */
        val squadronId: Int get() = data["api_plane_info"][id - 1]["api_squadron_id"].int()
        /**
         * 状態
         * 0=无飞机, 1=有飞机, 2=配置転換中
         */
        val state: Int get() = data["api_plane_info"][id - 1]["api_state"].int()
        /**
         * 装备固有id
         */
        val equipmentId: Int get() = data["api_plane_info"][id - 1]["api_slotid"].int()
        /**
         * 这个格子里面有几架飞机
         */
        val aircraftCurrent: Int get() = data["api_plane_info"][id - 1]["api_count"].int()
        /**
         * 这个格子最大能装多少飞机
         */
        val aircraftMax: Int get() = data["api_plane_info"][id - 1]["api_max_count"].int()
        /**
         * 1=正常, 2=黄脸, 3=红脸
         */
        val condition: Int get() = data["api_plane_info"][id - 1]["api_cond"].int()
    }

    companion object {
        fun getId(mapAreaId: Int, airCorpsId: Int) = mapAreaId * 10 + airCorpsId

        fun getId(requestMap: Map<String, String>): Int {
            val api_area_id = requestMap["api_area_id"]?.toInt() ?: return 0
            val api_base_id = requestMap["api_base_id"]?.toInt() ?: return 0
            return getId(api_area_id, api_base_id)
        }

        fun getId(api_data: JsonElement): Int {
            return getId(api_data["api_area_id"].int(), api_data["api_rid"].int())
        }
    }

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        when (apiName) {
            api_req_air_corps.expand_base.name,
            api_get_member.mapinfo.name -> {
                super.loadFromResponse(apiName, responseData)
            }
            api_req_air_corps.supply.name,
            api_req_air_corps.set_plane.name -> {
                data["api_distance"] = responseData["api_distance"].int()

                val planes = responseData["api_plane_info"].array ?: return
                for (plane in planes) {
                    val id = plane["api_squadron_id"].int()
                    data["api_plane_info"][id - 1] = plane
                }
            }
            else -> throw CatException()
        }
    }

    override fun loadFromRequest(apiName: String, requestData: MutableMap<String, String>) {
        when (apiName) {
            api_req_air_corps.change_name.name -> {
                val newName = requestData["api_name"] ?: return
                data["api_name"] = newName
            }
            api_req_air_corps.set_action.name -> {
                val action = requestData["api_action_kind"] ?: return
                data["api_action_kind"] = action.toInt()
            }
            else -> throw CatException()
        }
    }
}