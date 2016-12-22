package kandroid.utils.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.WildcardType
import java.util.*

val GSON: Gson by lazy {
    GsonBuilder().registerTypeAdapter(Date::class.java, object : TypeAdapter<Date>() {
        override fun write(_out: JsonWriter, date: Date) {
            _out.value(date.time)
        }

        override fun read(_in: JsonReader): Date? {
            return if (_in.hasNext()) Date(_in.nextLong()) else null
        }
    }).create()
}
val JsonParser: JsonParser = JsonParser()

inline fun <reified T : Any> gsonTypeToken(): Type = object : TypeToken<T>() {}.type

fun ParameterizedType.isWildcard(): Boolean {
    var hasAnyWildCard = false
    var hasBaseWildCard = false
    var hasSpecific = false

    val cls = this.rawType as Class<*>
    cls.typeParameters.forEachIndexed { i, variable ->
        val argument = actualTypeArguments[i]
        if (argument is WildcardType) {
            val hit = variable.bounds.firstOrNull { it in argument.upperBounds }
            if (hit != null) {
                if (hit == Any::class.java)
                    hasAnyWildCard = true
                else hasBaseWildCard = true
            } else hasSpecific = true
        } else hasSpecific = true
    }
    if (hasAnyWildCard && hasSpecific)
        throw IllegalArgumentException("Either none or all type parameters can be wildcard in $this")
    return hasAnyWildCard || (hasBaseWildCard && !hasSpecific)
}

@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
inline fun <reified T : Any> typeToken(): Type {
    val type = gsonTypeToken<T>()
    if (type is ParameterizedType && type.isWildcard())
        return type.rawType
    return type
}

