package kandroid.utils.json

import com.google.gson.*
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

class DebugJsonElement(val parent: JsonElement?, val name: String, val data: JsonElement) : JsonElement() {

    private fun getStringChain(): String {
        val names = ArrayList<String>()
        var len = 0
        var json: JsonElement? = this
        while (json is DebugJsonElement) {
            names.add(json.name)
            len += json.name.length
            json = json.parent
        }
        names.reverse()
        val sb = StringBuilder(len + names.size)
        names.forEach { sb.append('/').append(it) }
        return sb.toString()
    }

    //region Proxy
    override fun isJsonNull(): Boolean {
        return data.isJsonNull
    }

    override fun getAsJsonPrimitive(): JsonPrimitive {
        return data.asJsonPrimitive
    }

    override fun isJsonArray(): Boolean {
        return data.isJsonArray
    }

    override fun isJsonObject(): Boolean {
        return data.isJsonObject
    }

    override fun getAsByte(): Byte {
        return data.asByte
    }

    override fun getAsString(): String {
        return data.asString
    }

    override fun getAsLong(): Long {
        return data.asLong
    }

    override fun getAsFloat(): Float {
        return data.asFloat
    }

    override fun getAsBoolean(): Boolean {
        return data.asBoolean
    }

    override fun getAsBigInteger(): BigInteger {
        return data.asBigInteger
    }

    override fun getAsShort(): Short {
        return data.asShort
    }

    override fun getAsBigDecimal(): BigDecimal {
        return data.asBigDecimal
    }

    override fun toString(): String {
        return data.toString()
    }

    override fun getAsInt(): Int {
        return data.asInt
    }

    override fun getAsNumber(): Number {
        return data.asNumber
    }

    override fun isJsonPrimitive(): Boolean {
        return data.isJsonPrimitive
    }

    override fun getAsCharacter(): Char {
        return data.asCharacter
    }

    override fun getAsJsonObject(): JsonObject {
        return data.asJsonObject
    }

    override fun getAsDouble(): Double {
        return data.asDouble
    }

    override fun getAsJsonArray(): JsonArray {
        return data.asJsonArray
    }

    override fun getAsJsonNull(): JsonNull {
        return data.asJsonNull
    }
    //endregion
}