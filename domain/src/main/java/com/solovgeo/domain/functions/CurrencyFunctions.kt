package com.solovgeo.domain.functions

import com.solovgeo.domain.entity.CalculatedCurrencyValues
import com.solovgeo.domain.entity.CurrencyList
import java.math.BigDecimal

object CurrencyFunctions {

    fun calculateCurrencyValues(
        currencyCountBy: String,
        currencyValue: BigDecimal,
        currencyList: CurrencyList
    ): CalculatedCurrencyValues {
        val baseCurrencyValue = if (currencyCountBy == currencyList.baseCurrency) {
            currencyValue
        } else {
            currencyList.rates[currencyList.baseCurrency]!!
                .divide(currencyList.rates[currencyCountBy]!!)
                .multiply(currencyValue)
        }
        return CalculatedCurrencyValues(rates = currencyList.rates.mapValues {
            it.value.multiply(baseCurrencyValue)
        })
    }
}