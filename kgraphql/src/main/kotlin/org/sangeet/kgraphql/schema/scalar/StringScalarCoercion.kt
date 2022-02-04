package com.apurebase.kgraphql.schema.scalar

import kotlinx.serialization.json.JsonPrimitive


interface StringScalarCoercion<T> : ScalarCoercion<T, String> {
  override fun deserialize(json: JsonPrimitive) = deserialize(json.content)
}
