package com.apurebase.kgraphql.request

import com.apurebase.kgraphql.ExecutionException
import com.apurebase.kgraphql.GraphQLError
import com.apurebase.kgraphql.getIterableElementType
import com.apurebase.kgraphql.isIterable
import com.apurebase.kgraphql.schema.model.ast.NameNode
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.TypeFactory
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.serializer
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.jvm.jvmErasure

/**
 * Represents already parsed variables json
 */
interface VariablesJson {

    fun <T : Any> get(kClass: KClass<T>, kType: KType, key : NameNode) : T?

    class Empty : VariablesJson {
        override fun <T : Any> get(kClass: KClass<T>, kType: KType, key: NameNode): T? {
            return null
        }
    }

    class Defined(val json: JsonElement) : VariablesJson {

        constructor(json: String) : this(Json.parseToJsonElement(json))

        /**
         * map and return object of requested class
         */
        override fun <T : Any> get(kClass: KClass<T>, kType: KType, key : NameNode) : T? {
            require(kClass == kType.jvmErasure) { "kClass and KType must represent same class" }
            require(json is JsonObject) { "variable should be JsonObject not $json" }
            return json.let { node -> node[key.value] }?.let { tree ->
                try {
                    @Suppress("UNCHECKED_CAST")
                    Json.decodeFromJsonElement(Json.serializersModule.serializer(kType) as KSerializer<T>, tree)
                } catch(e : Exception) {
                    throw if (e is GraphQLError) e
                    else ExecutionException("Failed to coerce $tree as $kType", key, e)
                }
            }
        }
    }

    fun KType.toTypeReference(): JavaType {
        return if(jvmErasure.isIterable()) {
            val elementType = getIterableElementType()
                ?: throw ExecutionException("Cannot handle collection without element type")

            TypeFactory.defaultInstance().constructCollectionType(List::class.java, elementType.jvmErasure.java)
        } else {
            TypeFactory.defaultInstance().constructSimpleType(jvmErasure.java, emptyArray())
        }
    }
}
