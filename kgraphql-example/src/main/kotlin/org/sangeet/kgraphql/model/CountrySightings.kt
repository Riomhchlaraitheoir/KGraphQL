package org.sangeet.kgraphql.model


data class CountrySightings(
        var state: String = "",
        var country: String = "",
        var numOccurrences: Int = 0
)