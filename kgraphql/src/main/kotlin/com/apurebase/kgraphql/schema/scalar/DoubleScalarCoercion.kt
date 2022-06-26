package com.apurebase.kgraphql.schema.scalar

import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.double


interface DoubleScalarCoercion<T> : ScalarCoercion<T, Double> {
  override fun deserialize(json: JsonPrimitive) = deserialize(json.double)
}
