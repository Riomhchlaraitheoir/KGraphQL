package com.apurebase.kgraphql.schema.scalar

import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.int


interface IntScalarCoercion <T> : ScalarCoercion<T, Int> {
  override fun deserialize(json: JsonPrimitive) = deserialize(json.int)
}