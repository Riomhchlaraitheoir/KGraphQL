package org.sangeet.kgraphql.schema.introspection

import org.sangeet.kgraphql.Context
import org.sangeet.kgraphql.configuration.SchemaConfiguration
import org.sangeet.kgraphql.schema.execution.ExecutionOptions
import org.sangeet.kgraphql.schema.structure.LookupSchema
import org.sangeet.kgraphql.schema.structure.Type
import kotlin.reflect.KClass
import kotlin.reflect.KType


class SchemaProxy(
    override val configuration: SchemaConfiguration,
    var proxiedSchema : LookupSchema? = null
): LookupSchema {

    companion object {
        const val ILLEGAL_STATE_MESSAGE = "Missing proxied __Schema instance"
    }

    private fun getProxied() = proxiedSchema ?: throw IllegalStateException(ILLEGAL_STATE_MESSAGE)

    override val types: List<__Type>
        get() = getProxied().types

    override val queryType: __Type
        get() = getProxied().queryType

    override val mutationType: __Type?
        get() = getProxied().mutationType

    override val subscriptionType: __Type?
        get() = getProxied().subscriptionType

    override val directives: List<__Directive>
        get() = getProxied().directives

    override fun findTypeByName(name: String): __Type? = getProxied().findTypeByName(name)

    override fun typeByKClass(kClass: KClass<*>): Type? = getProxied().typeByKClass(kClass)

    override fun typeByKType(kType: KType): Type? = typeByKType(kType)

    override fun typeByName(name: String): Type? = typeByName(name)

    override fun inputTypeByKClass(kClass: KClass<*>): Type? = inputTypeByKClass(kClass)

    override fun inputTypeByKType(kType: KType): Type? = inputTypeByKType(kType)

    override fun inputTypeByName(name: String): Type? = inputTypeByName(name)

    override suspend fun execute(request: String, variables: String?, context: Context, options: ExecutionOptions): String {
        return getProxied().execute(request, variables, context, options)
    }
}
