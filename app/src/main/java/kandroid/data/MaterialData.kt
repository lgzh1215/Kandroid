package kandroid.data

import com.google.gson.JsonElement

class MaterialData {

    var fuel: Int = 0
    var ammo: Int = 0
    var steel: Int = 0
    var bauxite: Int = 0
    var instantConstruction: Int = 0
    var instantRepair: Int = 0
    var developmentMaterial: Int = 0
    var moddingMaterial: Int = 0

    fun loadFromResponse(apiName: String, data: JsonElement) {
        when (apiName) {
            "api_port/port", "api_get_member/material" -> {
            }

            "api_req_hokyu/charge", "api_req_kousyou/destroyship" -> {
            }

            "api_req_kousyou/destroyitem2" -> {
            }

            "api_req_kousyou/createitem", "api_req_kousyou/remodel_slot" -> {
            }
        }
    }
}