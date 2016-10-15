package kandroid.data

import com.google.gson.JsonElement
import kandroid.newdata.RequestDataListener
import kandroid.newdata.ResponseDataListener
import kandroid.utils.json.get
import kandroid.utils.json.int

class MaterialData : RequestDataListener, ResponseDataListener {

    var fuel: Int = 0
    var ammo: Int = 0
    var steel: Int = 0
    var bauxite: Int = 0
    var instantConstruction: Int = 0
    var instantRepair: Int = 0
    var developmentMaterial: Int = 0
    var moddingMaterial: Int = 0

    override fun loadFromRequest(apiName: String, requestData: Map<String, String>) {

    }

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        when (apiName) {
            "api_port/port",
            "api_get_member/material" -> {
                fuel = responseData[0]["api_value"].int(fuel)
                ammo = responseData[1]["api_value"].int(ammo)
                steel = responseData[2]["api_value"].int(steel)
                bauxite = responseData[3]["api_value"].int(bauxite)
                instantConstruction = responseData[4]["api_value"].int(instantConstruction)
                instantRepair = responseData[5]["api_value"].int(instantRepair)
                developmentMaterial = responseData[6]["api_value"].int(developmentMaterial)
                moddingMaterial = responseData[7]["api_value"].int(moddingMaterial)
            }
            "api_req_hokyu/charge",
            "api_req_kousyou/destroyship" -> {
            }

            "api_req_kousyou/destroyitem2" -> {
            }

            "api_req_kousyou/createitem",
            "api_req_kousyou/remodel_slot" -> {
            }
        }
    }
}