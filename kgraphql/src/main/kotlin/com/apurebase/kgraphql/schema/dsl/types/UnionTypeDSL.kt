package com.apurebase.kgraphql.schema.dsl.types

import com.apurebase.kgraphql.schema.dsl.ItemDSL
import kotlin.reflect.KClass


class UnionTypeDSL<T: Any>() : ItemDSL() {

    internal val possibleTypes = mutableSetOf<SubType<*>>()

    var subTypeBlock: TypeDSL<*>.() -> Unit = {}

    fun <S : T> type(kClass : KClass<S>, block: TypeDSL<S>.() -> Unit = {}){
        possibleTypes.removeIf { it.type == kClass }
        possibleTypes.add(SubType(kClass, block))
    }

    inline fun <reified S : T>type(noinline block: TypeDSL<S>.() -> Unit = {}){
        type(S::class, block)
    }
    
    internal data class SubType<S: Any>(val type: KClass<S>, val def: TypeDSL<S>.() -> Unit = {})
}
