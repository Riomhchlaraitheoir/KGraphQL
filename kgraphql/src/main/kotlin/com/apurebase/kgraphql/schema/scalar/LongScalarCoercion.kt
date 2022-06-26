package com.apurebase.kgraphql.schema.scalar

import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.long


interface LongScalarCoercion<T> : ScalarCoercion<T, Long> {
  override fun deserialize(json: JsonPrimitive) = deserialize(json.long)
}