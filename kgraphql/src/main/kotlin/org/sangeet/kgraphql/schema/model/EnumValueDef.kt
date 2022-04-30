package org.sangeet.kgraphql.schema.model

import org.sangeet.kgraphql.schema.structure.EnumValue


class EnumValueDef<T : Enum<T>>(
        val value: T,
        override val description: String? = null,
        override val isDeprecated: Boolean = false,
        override val deprecationReason: String? = null
) : DescribedDef, Depreciable {
    val name = value.name

    fun toEnumValue (): EnumValue<T> {
        return EnumValue(this)
    }
}
