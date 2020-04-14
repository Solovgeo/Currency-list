package com.solovgeo.domain

import com.solovgeo.domain.entity.Currency
import com.solovgeo.domain.entity.CurrencyList
import com.solovgeo.domain.functions.CurrencyFunctions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CurrencyFunctionsTest {

    @Test
    fun `test don't add base currency to rates`() {
        val currencyValue = BigDecimal.ONE
        val currencyList = CurrencyList(baseCurrency = "EUR", rates = mapOf("USD" to BigDecimal("1.1"), "RUB" to BigDecimal("76.9")))
        val result = CurrencyFunctions.calculateCurrencyValues(currencyValue, currencyList)

        assertEquals(2, result.rates.size)
        assertEquals(Currency("EUR", BigDecimal.ONE), result.baseCurrency)
        assertTrue(result.rates["USD"] != null)
        assertTrue(result.rates["RUB"] != null)
        assertEquals(null, result.rates["EUR"])
    }

    @Test
    fun `test rates calculation`() {
        val currencyValue = BigDecimal("1.6")
        val currencyList = CurrencyList(baseCurrency = "EUR", rates = mapOf("USD" to BigDecimal("1.1"), "RUB" to BigDecimal("76.9")))
        val result = CurrencyFunctions.calculateCurrencyValues(currencyValue, currencyList)

        assertEquals(2, result.rates.size)
        assertEquals(Currency("EUR", BigDecimal("1.6")), result.baseCurrency)
        assertTrue((result.rates["USD"] ?: error("")).compareTo(BigDecimal("1.76")) == 0)
        assertTrue((result.rates["RUB"] ?: error("")).compareTo(BigDecimal("123.04")) == 0)
        assertEquals(null, result.rates["EUR"])
    }
}
