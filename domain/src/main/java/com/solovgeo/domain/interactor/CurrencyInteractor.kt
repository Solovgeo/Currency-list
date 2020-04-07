package com.solovgeo.domain.interactor

import com.solovgeo.domain.entity.Currency
import com.solovgeo.domain.entity.CurrencyList
import com.solovgeo.domain.functions.CurrencyFunctions
import com.solovgeo.domain.repository.CurrencyRepository
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.math.BigDecimal
import java.util.concurrent.TimeUnit


class CurrencyInteractor constructor(private val currencyRepository: CurrencyRepository) {

    private val observable = Observable.interval(1L, TimeUnit.SECONDS)
    private val currentCurrencyPublishSubject = BehaviorSubject.createDefault(Currency("", BigDecimal.ONE))

    fun startSync(): Observable<CurrencyList> {
        return observable.flatMap { currentCurrencyPublishSubject }
            .mergeWith(currentCurrencyPublishSubject)
            .concatMapSingle { currency ->
                currencyRepository.getCurrencyList(currency.name).map { it to currency }
            }.map {
                CurrencyFunctions.calculateCurrencyValues(it.second.name, it.second.value, it.first)
            }
    }

    fun changeCurrency(currency: Currency) {
        currentCurrencyPublishSubject.onNext(currency)
    }
}