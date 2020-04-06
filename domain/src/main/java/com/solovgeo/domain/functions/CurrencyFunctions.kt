package com.solovgeo.domain.functions

import com.solovgeo.domain.entity.CurrencyList
import java.math.BigDecimal
import java.math.RoundingMode

object CurrencyFunctions {

    fun calculateCurrencyValues(
        currencyCountBy: String,
        currencyValue: BigDecimal,
        currencyList: CurrencyList
    ): CurrencyList {

        val rates = currencyList.rates
            .toMutableMap()
            .apply { this[currencyList.baseCurrency] = BigDecimal.ONE }
        val baseCurrencyRate = rates[currencyCountBy]
        val baseCurrency = if (baseCurrencyRate != null) currencyCountBy else currencyList.baseCurrency
        val baseCurrencyValue = currencyValue.divide(baseCurrencyRate ?: BigDecimal.ONE, 5, RoundingMode.HALF_UP)

        return CurrencyList(baseCurrency = baseCurrency, rates = rates.mapValues { it.value.multiply(baseCurrencyValue) })
    }
}