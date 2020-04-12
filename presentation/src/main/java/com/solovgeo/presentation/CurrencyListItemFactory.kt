package com.solovgeo.presentation

import java.math.BigDecimal
import javax.inject.Inject

class CurrencyListItemFactory @Inject constructor() {

    private val iconDescriptionMap = hashMapOf(
        "USD" to (R.drawable.ic_us to R.string.usd_description),
        "EUR" to (R.drawable.ic_eu to R.string.eu_description),
        "SEK" to (R.drawable.ic_sweden to R.string.sweden_description),
        "CAD" to (R.drawable.ic_canada to R.string.canada_description),
        "AUD" to (R.drawable.ic_australia to R.string.australia_description),
        "BGN" to (R.drawable.ic_bulgaria to R.string.bulgaria_description),
        "CHF" to (R.drawable.ic_switzerland to R.string.switzerland_description),
        "CNY" to (R.drawable.ic_china to R.string.china_description),
        "BRL" to (R.drawable.ic_brazil to R.string.brazil_description),
        "CZK" to (R.drawable.ic_czech_republic to R.string.czech_republic_description),
        "DKK" to (R.drawable.ic_denmark to R.string.denmark_description),
        "GBP" to (R.drawable.ic_england to R.string.england_description),
        "HKD" to (R.drawable.ic_hong_kong to R.string.hong_kong_description),
        "HRK" to (R.drawable.ic_croatia to R.string.croatia_description),
        "HUF" to (R.drawable.ic_hungary to R.string.hungary_description),
        "IDR" to (R.drawable.ic_indonesia to R.string.indonesia_description),
        "ILS" to (R.drawable.ic_israel to R.string.israel_description),
        "INR" to (R.drawable.ic_india to R.string.india_description),
        "ISK" to (R.drawable.ic_iceland to R.string.iceland_description),
        "JPY" to (R.drawable.ic_japan to R.string.japan_description),
        "KRW" to (R.drawable.ic_south_korea to R.string.south_korea_description),
        "MXN" to (R.drawable.ic_mexico to R.string.mexico_description),
        "MYR" to (R.drawable.ic_malaysia to R.string.malaysia_description),
        "NOK" to (R.drawable.ic_norway to R.string.norway_description),
        "NZD" to (R.drawable.ic_new_zealand to R.string.new_zealand_description),
        "PHP" to (R.drawable.ic_philippines to R.string.philippines_description),
        "PLN" to (R.drawable.ic_republic_of_poland to R.string.republic_of_poland_description),
        "RON" to (R.drawable.ic_romania to R.string.romania_description),
        "RUB" to (R.drawable.ic_russia to R.string.russia_description),
        "SGD" to (R.drawable.ic_singapore to R.string.singapore_description),
        "THB" to (R.drawable.ic_thailand to R.string.thailand_description),
        "ZAR" to (R.drawable.ic_south_africa to R.string.south_africa_description)
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