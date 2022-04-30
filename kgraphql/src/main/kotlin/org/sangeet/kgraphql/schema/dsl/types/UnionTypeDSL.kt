package org.sangeet.kgraphql.schema.dsl.types

import org.sangeet.kgraphql.schema.dsl.ItemDSL
import kotlin.reflect.KClass


class UnionTypeDSL() : ItemDSL() {

    internal val possibleTypes = mutableSetOf<KClass<*>>()

    var subTypeBlock: TypeDSL<*>.() -> Unit = {}

    fun <T : Any>type(kClass : KClass<T>){
        possibleTypes.add(kClass)
    }

    inline fun <reified T : Any>type(){
        type(T::class)
    }
}
