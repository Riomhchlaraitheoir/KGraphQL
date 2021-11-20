package com.apurebase.kgraphql.schema.execution

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.reflect.KMutableProperty0

@Serializable(with = OptionalValueSerializer::class)
sealed class OptionalValue<out T: Any> {
  data class Defined<T: Any>(val value: T?): OptionalValue<T>()
  object Undefined: OptionalValue<Nothing>()

  operator fun invoke(consumer: (T?) -> Unit) {
    if (this is Defined) consumer(value)
  }

  operator fun invoke(property: KMutableProperty0<in T?>) {
    if (this is Defined) property.set(value)
  }
}

class OptionalValueSerializer<T: Any>(private val valueSerializer: KSerializer<T?>): KSerializer<OptionalValue<T>> {
  @OptIn(ExperimentalSerializationApi::class)
  override fun deserialize(decoder: Decoder): OptionalValue<T> {
    return OptionalValue.Defined(decoder.decodeNullableSerializableValue(valueSerializer))
  }

  override val descriptor: SerialDescriptor = valueSerializer.descriptor

  override fun serialize(encoder: Encoder, value: OptionalValue<T>) {
    if (value is OptionalValue.Defined)
      valueSerializer.serialize(encoder, value.value)
  }
}