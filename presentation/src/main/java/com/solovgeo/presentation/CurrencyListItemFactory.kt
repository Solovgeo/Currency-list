package com.solovgeo.presentation

import java.math.BigDecimal

class CurrencyListItemFactory {

    private val iconDescriptionMap = hashMapOf(
        "USD" to (R.drawable.ic_us to R.string.usd_description),
        "EUR" to (R.drawable.ic_eu to R.string.eu_description),
        "USD" to (R.drawable.ic_sweden to R.string.sweden_description),
        "USD" to (R.drawable.ic_canada to R.string.canada_description)
    )

    fun create(currencyName: String, currencyValue: BigDecimal): CurrencyListItem {
        val currencyIconRes = iconDescriptionMap[currencyName]?.first ?: R.drawable.ic_not_interested_black
        val currencyDescriptionRes = iconDescriptionMap[currencyName]?.second ?: R.string.empty
        return CurrencyListItem(
            currencyIconRes = currencyIconRes,
            currencyTitle = currencyName,
            currencyDescriptionRes = currencyDescriptionRes,
            currencyValue = currencyValue
        )
    }
}