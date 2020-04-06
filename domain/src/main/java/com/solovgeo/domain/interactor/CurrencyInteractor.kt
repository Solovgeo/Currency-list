package com.solovgeo.domain.interactor

import com.solovgeo.domain.entity.CalculatedCurrencyValues
import com.solovgeo.domain.entity.CurrencyList
import com.solovgeo.domain.functions.CurrencyFunctions
import com.solovgeo.domain.repository.CurrencyRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import java.math.BigDecimal
import java.util.concurrent.TimeUnit


class CurrencyInteractor constructor(private val currencyRepository: CurrencyRepository) {

    private var currency: String = ""
    private var value = BigDecimal.ZERO

    private val observable = Observable.interval(1L, TimeUnit.SECONDS)
    private val publishSubject = PublishSubject.create<CurrencyList>()

    fun startSync(): Observable<CalculatedCurrencyValues> {
        return observable.flatMapSingle {
            currencyRepository.getCurrencyList()
        }
            .mergeWith(publishSubject)
            .map {
                CurrencyFunctions.calculateCurrencyValues(currency, value, it)
            }
    }

    fun changeCurrency(currency: String, value: BigDecimal) {
        this.currency = currency
        this.value = value
    }

    fun getCurrencyList(): Single<CurrencyList> {
        return currencyRepository.getCurrencyList()
    }


}