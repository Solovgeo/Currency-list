package com.solovgeo.currencylist.mock

import com.solovgeo.data.network.ServiceApi
import com.solovgeo.data.network.dto.ApiCurrencyList
import io.reactivex.Single
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

class ApiMock(var rates: Map<String, BigDecimal> = mapOf("USD" to BigDecimal.ONE)) : ServiceApi {
    override fun getCurrencyList(baseCurrency: String?): Single<ApiCurrencyList> {
        return Single.just(ApiCurrencyList(baseCurrency = if (baseCurrency.isNullOrBlank()) "EUR" else baseCurrency, rates = rates))
            .delay(500, TimeUnit.MILLISECONDS)
    }
}