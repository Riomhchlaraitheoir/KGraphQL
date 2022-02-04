package com.apurebase.kgraphql.schema.scalar

import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean


interface BooleanScalarCoercion <T> : ScalarCoercion<T, Boolean> {
  override fun deserialize(json: JsonPrimitive) = deserialize(json.boolean)
}
