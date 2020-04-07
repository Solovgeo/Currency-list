package com.solovgeo.domain.interactor

import com.solovgeo.domain.entity.Currency
import com.solovgeo.domain.entity.CurrencyList
import com.solovgeo.domain.functions.CurrencyFunctions
import com.solovgeo.domain.repository.CurrencyRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class CurrencyInteractor constructor(private val currencyRepository: CurrencyRepository) {

    private val observable = Observable.interval(1L, TimeUnit.SECONDS)
    private val currentCurrencyPublishSubject = PublishSubject.create<Currency>()

    fun startSync(): Observable<CurrencyList> {
        return Observable.combineLatest(
            getCurrencyListObservable(),
            currentCurrencyPublishSubject,
            BiFunction { currencyList: CurrencyList, currency: Currency ->
                CurrencyFunctions.calculateCurrencyValues(currency.name, currency.value, currencyList)
            })
    }

    fun changeCurrency(currency: Currency) {
        currentCurrencyPublishSubject.onNext(currency)
    }

    fun getCurrencyList(): Single<CurrencyList> {
        return currencyRepository.getCurrencyList()
    }

    private fun getCurrencyListObservable(): Observable<CurrencyList> {
        return observable.flatMapSingle {
            currencyRepository.getCurrencyList()
        }
    }


}