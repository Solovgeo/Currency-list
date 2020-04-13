package com.solovgeo.domain.entity

import java.math.BigDecimal

data class Currency(val name: String, val value: BigDecimal) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Currency

        if (name != other.name) return false
        if (value.compareTo(other.value) != 0) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }
}