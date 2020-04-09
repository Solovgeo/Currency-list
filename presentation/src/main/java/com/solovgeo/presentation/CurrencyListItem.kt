package com.solovgeo.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import java.math.BigDecimal

data class CurrencyListItem(
    @DrawableRes val currencyIconRes: Int,
    val currencyTitle: String,
    @StringRes val currencyDescriptionRes: Int,
    val currencyValue: BigDecimal
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CurrencyListItem

        if (currencyIconRes != other.currencyIconRes) return false
        if (currencyTitle != other.currencyTitle) return false
        if (currencyDescriptionRes != other.currencyDescriptionRes) return false
        if (currencyValue.compareTo(other.currencyValue) != 0) return false

        return true
    }

    override fun hashCode(): Int {
        var result = currencyIconRes
        result = 31 * result + currencyTitle.hashCode()
        result = 31 * result + currencyDescriptionRes
        result = 31 * result + currencyValue.hashCode()
        return result
    }
}