package org.sangeet.kgraphql.schema.model


data class Deprecation<T> (val target: T, val reason: String?)