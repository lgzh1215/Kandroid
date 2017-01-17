package kandroid.utils.json

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import kandroid.data.JsonWrapper
import java.util.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

operator fun JsonObject.getValue(thisRef: Any?, property: KProperty<*>): JsonElement = this[property.name]

operator fun JsonObject.setValue(thisRef: Any?, property: KProperty<*>, value: JsonElement) { this[property.name] = value }

class JsonObjectDelegate<T : Any>(private val wrapper: JsonWrapper,
                                  private val getMethod: (JsonElement) -> T,
                                  private val setMethod: (T) -> JsonElement,
                                  private val key: String? = null,
                                  private val default: (() -> T)? = null) : ReadWriteProperty<Any?, T> {

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val element = wrapper.data[key ?: property.name]
        if(element === jsonNull) {
            val default = default
            if (default == null)
                throw NoSuchElementException("'$key' not found")
            else
                return default()
        }
        return getMethod(element)
    }

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        wrapper.data[key ?: property.name] = setMethod(value)
    }

}

class NullableJsonObjectDelegate<T: Any?>(private val wrapper: JsonWrapper,
                                          private val getMethod: (JsonElement) -> T?,
                                          private val setMethod: (T?) -> JsonElement,
                                          private val _key: String? = null,
                                          private val _default: (() -> T)? = null) : ReadWriteProperty<Any?, T?> {

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        val element = wrapper.data[_key ?: property.name]
        if (element === jsonNull)
            return _default?.invoke()
        return getMethod(element)
    }

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        wrapper.data[_key ?: property.name] = setMethod(value)
    }

}

class JsonArrayDelegate<T: Any>(private val wrapper: JsonWrapper,
                                private val index: Int,
                                private val getMethod: (JsonElement) -> T,
                                private val setMethod: (T) -> JsonElement) : ReadWriteProperty<Any?, T> {

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T = getMethod(wrapper.data[index])

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) { wrapper.data[index] = setMethod(value) }

}

fun Number.toJson(): JsonPrimitive = JsonPrimitive(this)

fun String.toJson() : JsonPrimitive = JsonPrimitive(this)

//region @formatter:off
val JsonWrapper.byString     : JsonObjectDelegate<String>     get() = JsonObjectDelegate(this, { it.asString     }, { it.toJson() } )
val JsonWrapper.byInt        : JsonObjectDelegate<Int>        get() = JsonObjectDelegate(this, { it.asInt        }, { it.toJson() } )
val JsonWrapper.byLong       : JsonObjectDelegate<Long>       get() = JsonObjectDelegate(this, { it.asLong       }, { it.toJson() } )
val JsonWrapper.byDouble     : JsonObjectDelegate<Double>     get() = JsonObjectDelegate(this, { it.asDouble     }, { it.toJson() } )
val JsonWrapper.byArray      : JsonObjectDelegate<JsonArray>  get() = JsonObjectDelegate(this, { it.asJsonArray  }, { it          } )
val JsonWrapper.byObject     : JsonObjectDelegate<JsonObject> get() = JsonObjectDelegate(this, { it.asJsonObject }, { it          } )

fun JsonWrapper.byString     ( key: String? = null, default: ( () -> String     )? = null ) : JsonObjectDelegate<String>     = JsonObjectDelegate(this, { it.asString     }, { it.toJson() }, key, default )
fun JsonWrapper.byInt        ( key: String? = null, default: ( () -> Int        )? = null ) : JsonObjectDelegate<Int>        = JsonObjectDelegate(this, { it.asInt        }, { it.toJson() }, key, default )
fun JsonWrapper.byLong       ( key: String? = null, default: ( () -> Long       )? = null ) : JsonObjectDelegate<Long>       = JsonObjectDelegate(this, { it.asLong       }, { it.toJson() }, key, default )
fun JsonWrapper.byDouble     ( key: String? = null, default: ( () -> Double     )? = null ) : JsonObjectDelegate<Double>     = JsonObjectDelegate(this, { it.asDouble     }, { it.toJson() }, key, default )
fun JsonWrapper.byArray      ( key: String? = null, default: ( () -> JsonArray  )? = null ) : JsonObjectDelegate<JsonArray>  = JsonObjectDelegate(this, { it.asJsonArray  }, { it          }, key, default )
fun JsonWrapper.byObject     ( key: String? = null, default: ( () -> JsonObject )? = null ) : JsonObjectDelegate<JsonObject> = JsonObjectDelegate(this, { it.asJsonObject }, { it          }, key, default )

val JsonWrapper.byNullableString     : NullableJsonObjectDelegate<String?>     get() = NullableJsonObjectDelegate(this, { it.asString     }, { it?.toJson() ?: jsonNull } )
val JsonWrapper.byNullableInt        : NullableJsonObjectDelegate<Int?>        get() = NullableJsonObjectDelegate(this, { it.asInt        }, { it?.toJson() ?: jsonNull } )
val JsonWrapper.byNullableLong       : NullableJsonObjectDelegate<Long?>       get() = NullableJsonObjectDelegate(this, { it.asLong       }, { it?.toJson() ?: jsonNull } )
val JsonWrapper.byNullableDouble     : NullableJsonObjectDelegate<Double?>     get() = NullableJsonObjectDelegate(this, { it.asDouble     }, { it?.toJson() ?: jsonNull } )
val JsonWrapper.byNullableArray      : NullableJsonObjectDelegate<JsonArray?>  get() = NullableJsonObjectDelegate(this, { it.asJsonArray  }, { it           ?: jsonNull } )
val JsonWrapper.byNullableObject     : NullableJsonObjectDelegate<JsonObject?> get() = NullableJsonObjectDelegate(this, { it.asJsonObject }, { it           ?: jsonNull } )

fun JsonWrapper.byNullableString     (key: String? = null, default: ( () -> String     )? = null) : NullableJsonObjectDelegate<String?>     = NullableJsonObjectDelegate(this, { it.asString     }, { it?.toJson() ?: jsonNull }, key, default )
fun JsonWrapper.byNullableInt        (key: String? = null, default: ( () -> Int        )? = null) : NullableJsonObjectDelegate<Int?>        = NullableJsonObjectDelegate(this, { it.asInt        }, { it?.toJson() ?: jsonNull }, key, default )
fun JsonWrapper.byNullableLong       (key: String? = null, default: ( () -> Long       )? = null) : NullableJsonObjectDelegate<Long?>       = NullableJsonObjectDelegate(this, { it.asLong       }, { it?.toJson() ?: jsonNull }, key, default )
fun JsonWrapper.byNullableDouble     (key: String? = null, default: ( () -> Double     )? = null) : NullableJsonObjectDelegate<Double?>     = NullableJsonObjectDelegate(this, { it.asDouble     }, { it?.toJson() ?: jsonNull }, key, default )
fun JsonWrapper.byNullableArray      (key: String? = null, default: ( () -> JsonArray  )? = null) : NullableJsonObjectDelegate<JsonArray?>  = NullableJsonObjectDelegate(this, { it.asJsonArray  }, { it           ?: jsonNull }, key, default )
fun JsonWrapper.byNullableObject     (key: String? = null, default: ( () -> JsonObject )? = null) : NullableJsonObjectDelegate<JsonObject?> = NullableJsonObjectDelegate(this, { it.asJsonObject }, { it           ?: jsonNull }, key, default )

fun JsonWrapper.byString     (index: Int) : JsonArrayDelegate<String>     = JsonArrayDelegate(this, index, { it.string()     }, { it.toJson() } )
fun JsonWrapper.byInt        (index: Int) : JsonArrayDelegate<Int>        = JsonArrayDelegate(this, index, { it.int()        }, { it.toJson() } )
fun JsonWrapper.byLong       (index: Int) : JsonArrayDelegate<Long>       = JsonArrayDelegate(this, index, { it.long()       }, { it.toJson() } )
fun JsonWrapper.byDouble     (index: Int) : JsonArrayDelegate<Double>     = JsonArrayDelegate(this, index, { it.double()     }, { it.toJson() } )
fun JsonWrapper.byArray      (index: Int) : JsonArrayDelegate<JsonArray>  = JsonArrayDelegate(this, index, { it.asJsonArray  }, { it          } )
fun JsonWrapper.byObject     (index: Int) : JsonArrayDelegate<JsonObject> = JsonArrayDelegate(this, index, { it.asJsonObject }, { it          } )
//endregion @formatter:on
