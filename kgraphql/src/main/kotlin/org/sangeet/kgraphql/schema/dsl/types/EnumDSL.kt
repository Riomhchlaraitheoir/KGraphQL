package org.sangeet.kgraphql.schema.dsl.types

import org.sangeet.kgraphql.defaultKQLTypeName
import org.sangeet.kgraphql.schema.dsl.ItemDSL
import kotlin.reflect.KClass


class EnumDSL<T : Enum<T>>(kClass: KClass<T>) : ItemDSL() {

    var name = kClass.defaultKQLTypeName()

    val valueDefinitions = mutableMapOf<T, EnumValueDSL<T>>()

    fun value(value : T, block : EnumValueDSL<T>.() -> Unit){
        valueDefinitions[value] = EnumValueDSL(value).apply(block)
    }

    infix fun T.describe(content: String){
        valueDefinitions[this] = EnumValueDSL(this).apply {
            description = content
        }
    }

}