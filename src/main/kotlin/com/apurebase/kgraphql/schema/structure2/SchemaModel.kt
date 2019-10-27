package com.apurebase.kgraphql.schema.structure2

import com.apurebase.kgraphql.schema.directive.Directive
import com.apurebase.kgraphql.schema.introspection.NotIntrospected
import com.apurebase.kgraphql.schema.introspection.__Schema
import com.apurebase.kgraphql.schema.introspection.__Type
import java.util.concurrent.Flow
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation


data class SchemaModel (
        val query: Type,
        val mutation: Type?,
        val subscription: Type?,
        val enums: Map<KClass<out Enum<*>>, Type.Enum<out Enum<*>>>,
        val scalars : Map<KClass<*>, Type.Scalar<*>>,
        val unions : List<Type.Union>,
        val allTypes : List<Type>,
        val queryTypes: Map<KClass<*>, Type>,
        val inputTypes: Map<KClass<*>, Type>,
        override val directives: List<Directive>
) : __Schema {

    val allTypesByName = allTypes.associate { it.name to it }

    val queryTypesByName = queryTypes.values.associate { it.name to it }

    val inputTypesByName = inputTypes.values.associate { it.name to it }

    override val types: List<__Type> = toTypeList()

    private fun toTypeList(): List<__Type> {
        var list = allTypes.toList()
                //workaround on the fact that Double and Float are treated as GraphQL Float
                .filterNot { it is Type.Scalar<*> && it.kClass == Float::class }
                .filterNot { it.kClass?.findAnnotation<NotIntrospected>() != null }
                //query and mutation must be present in introspection 'types' field for introspection tools
                .plus(query)
        if (mutation != null) list = list.plus(mutation)
        if (subscription != null) list = list.plus(subscription)
        return list
    }

    override val queryType: __Type = query

    override val mutationType: __Type? = mutation

    override val subscriptionType: __Type? = subscription

    override fun findTypeByName(name: String): __Type? = allTypesByName[name]
}