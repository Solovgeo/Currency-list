package com.solovgeo.currencylist

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import java.math.BigDecimal

data class CurrencyListItem(
    @DrawableRes val currencyIconRes: Int,
    val currencyTitle: String,
    @StringRes val currencyDescriptionRes: Int,
    val currencyValue: BigDecimal
)