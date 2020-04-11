package com.solovgeo.domain.interactor

import com.solovgeo.domain.entity.Currency
import com.solovgeo.domain.entity.CurrencyListCalculated
import com.solovgeo.domain.functions.CurrencyFunctions
import com.solovgeo.domain.repository.CurrencyRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.math.BigDecimal
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class CurrencyInteractor @Inject constructor(private val currencyRepository: CurrencyRepository) {

    private val observable = Observable.interval(1L, TimeUnit.SECONDS).toFlowable(BackpressureStrategy.DROP)
    private val currentCurrencyPublishSubject = BehaviorSubject.createDefault(Currency("", BigDecimal.ONE))
    private val currentCurrencyFlowable = currentCurrencyPublishSubject.toFlowable(BackpressureStrategy.DROP)

    fun startSync(): Flowable<CurrencyListCalculated> {
        return observable.switchMap { currentCurrencyFlowable }
            .switchMapSingle { currency ->
                currencyRepository.getCurrencyList(currency.name).map { it to currency }
            }.map {
                CurrencyFunctions.calculateCurrencyValues(it.second.value, it.first)
            }
    }

    fun changeCurrency(currency: Currency) {
        currentCurrencyPublishSubject.onNext(currency)
    }
}