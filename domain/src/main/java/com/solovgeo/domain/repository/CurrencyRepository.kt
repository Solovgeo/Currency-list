package com.solovgeo.domain.repository

import com.solovgeo.domain.entity.CurrencyList
import io.reactivex.Single

interface CurrencyRepository {
    fun getCurrencyList(baseValue: String?): Single<CurrencyList>
}