package com.apurebase.kgraphql.schema.scalar

import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.int

interface ShortScalarCoercion<T> : ScalarCoercion<T, Short> {
  override fun deserialize(json: JsonPrimitive) = deserialize(json.int.toShort())
}