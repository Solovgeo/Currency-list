package com.solovgeo.data.repository

import com.solovgeo.core.extentions.subscribeOnIo
import com.solovgeo.data.network.ServiceApi
import com.solovgeo.domain.entity.CurrencyList
import com.solovgeo.domain.repository.CurrencyRepository
import io.reactivex.Single
import javax.inject.Inject

class DefaultCurrencyRepository @Inject constructor(
    private val serviceApi: ServiceApi
) : CurrencyRepository {

    override fun getCurrencyList(): Single<CurrencyList> {
        return serviceApi.getCurrencyList()
            .map { CurrencyList(baseCurrency = it.baseCurrency, rates = it.rates) }
            .subscribeOnIo()
    }

}