package kandroid.data

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import kandroid.observer.kcsapi.*
import kandroid.utils.exception.CatException
import kandroid.utils.json.array
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

    override fun loadFromRequest(apiName: String, requestData: MutableMap<String, String>) {
        when (apiName) {
            api_req_kousyou.createship.name -> {
                fuel -= requestData["api_item1", 0]
                ammo -= requestData["api_item2", 0]
                steel -= requestData["api_item3", 0]
                bauxite -= requestData["api_item4", 0]
                developmentMaterial -= requestData["api_item5", 0]
            }
            api_req_nyukyo.speedchange.name -> {
                instantRepair--
            }
            else -> CatException()
        }
    }

    override fun loadFromResponse(apiName: String, responseData: JsonElement) {
        when (apiName) {
            api_port.port.name,
            api_get_member.material.name -> {
                fuel = responseData[0]["api_value"].int(fuel)
                ammo = responseData[1]["api_value"].int(ammo)
                steel = responseData[2]["api_value"].int(steel)
                bauxite = responseData[3]["api_value"].int(bauxite)
                instantConstruction = responseData[4]["api_value"].int(instantConstruction)
                instantRepair = responseData[5]["api_value"].int(instantRepair)
                developmentMaterial = responseData[6]["api_value"].int(developmentMaterial)
                moddingMaterial = responseData[7]["api_value"].int(moddingMaterial)
            }
            api_req_kousyou.destroyship.name,
            api_req_hokyu.charge.name -> {
                if (responseData is JsonArray) {
                    fuel = responseData[0].int(fuel)
                    ammo = responseData[1].int(ammo)
                    steel = responseData[2].int(steel)
                    bauxite = responseData[3].int(bauxite)
                } else throw CatException()
            }
            api_req_kousyou.destroyitem2.name -> {
                if (responseData is JsonArray) {
                    fuel += responseData[0].int()
                    ammo += responseData[1].int()
                    steel += responseData[2].int()
                    bauxite += responseData[3].int()
                } else throw CatException()
            }
            api_req_kousyou.createitem.name,
            api_req_kousyou.remodel_slot.name -> {
                if (responseData is JsonArray) {
                    fuel = responseData[0].int(fuel)
                    ammo = responseData[1].int(ammo)
                    steel = responseData[2].int(steel)
                    bauxite = responseData[3].int(bauxite)
                    instantConstruction = responseData[4].int(instantConstruction)
                    instantRepair = responseData[5].int(instantRepair)
                    developmentMaterial = responseData[6].int(developmentMaterial)
                    moddingMaterial = responseData[7].int(moddingMaterial)
                } else throw CatException()
            }
            api_req_air_corps.supply.name,
            api_req_air_corps.set_plane.name -> {
                fuel = responseData["api_after_fuel"].int(fuel)
                bauxite = responseData["api_after_bauxite"].int(bauxite)
            }
        }
    }
}