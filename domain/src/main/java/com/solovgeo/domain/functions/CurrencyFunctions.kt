package com.solovgeo.domain.functions

import com.solovgeo.domain.entity.CurrencyList
import java.math.BigDecimal

object CurrencyFunctions {

    fun calculateCurrencyValues(
        currencyCountBy: String,
        currencyValue: BigDecimal,
        currencyList: CurrencyList
    ): CurrencyList {
        return CurrencyList(baseCurrency = currencyCountBy, rates = currencyList.rates.mapValues { it.value.multiply(currencyValue) })
    }
}