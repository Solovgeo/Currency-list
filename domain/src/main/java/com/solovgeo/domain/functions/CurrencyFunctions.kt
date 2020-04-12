package com.solovgeo.domain.functions

import com.solovgeo.domain.entity.Currency
import com.solovgeo.domain.entity.CurrencyList
import com.solovgeo.domain.entity.CurrencyListCalculated
import java.math.BigDecimal
import java.math.RoundingMode

object CurrencyFunctions {

    fun calculateCurrencyValues(
        currencyValue: BigDecimal,
        currencyList: CurrencyList
    ): CurrencyListCalculated {
        return CurrencyListCalculated(
            baseCurrency = Currency(currencyList.baseCurrency, currencyValue),
            rates = currencyList.rates.mapValues { it.value.multiply(currencyValue).setScale(3, RoundingMode.HALF_UP) })
    }
}