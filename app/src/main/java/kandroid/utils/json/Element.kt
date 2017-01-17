package kandroid.utils.json

import com.google.gson.*
import kandroid.utils.log.Logger

val jsonNull: JsonNull = JsonNull.INSTANCE

private inline fun <T : Any> JsonElement.get(get: JsonElement.() -> T, default: T): T {
    if (this === jsonNull) return default
    try {
        return get()
    } catch (e: Exception) {
        Logger.e("Json Parse Error", e)
    }
    return default
}

fun JsonElement.string(default: String = "null"): String = get({ asString }, default)

fun JsonElement.int(default: Int = 0): Int = get({ asInt }, default)

fun JsonElement.long(default: Long = 0L): Long = get({ asLong }, default)

fun JsonElement.double(default: Double = 0.0): Double = get({ asDouble }, default)

fun <T> JsonElement.list(default: List<T> = emptyList()): List<T> =
        GSON.fromJson(this, typeToken<List<T>>()) ?: default

val JsonElement.obj: JsonObject? get() = if (this is JsonObject) this else null

val JsonElement.array: JsonArray? get() = if (this is JsonArray) this else null

operator fun JsonElement.get(key: String): JsonElement {
    if (this === jsonNull) return jsonNull
    return obj?.get(key) ?: jsonNull
}

operator fun JsonElement.get(index: Int): JsonElement {
    if (this === jsonNull) return jsonNull
    return array?.get(index) ?: jsonNull
}

operator fun JsonElement.set(key: String, value: Any) {
    obj?.add(key, value.toJsonElement())
}

operator fun JsonElement.set(key: Int, value: Any) {
    array?.set(key, value.toJsonElement())
}

fun JsonElement.search(key: String): JsonElement {
    var result: JsonElement = jsonNull
    when (this) {
        is JsonObject -> {
            result = get(key)
            if (result == null) {
                for ((k, v) in entrySet()) {
                    result = v.search(key)
                    if (result !== jsonNull) break
                }
            }
        }
        is JsonArray -> {
            for (j in this) {
                result = j.search(key)
                if (result !== jsonNull) break
            }
        }
    }
    return result
}

private fun Any.toJsonElement() = when (this) {
    is JsonElement -> this
    is Number -> JsonPrimitive(this)
    is String -> JsonPrimitive(this)
    else -> GSON.toJsonTree(this)
}
