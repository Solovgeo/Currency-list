package com.solovgeo.currencylist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.solovgeo.core.extentions.observeOnUi
import com.solovgeo.currencylist.base.BaseViewModel
import com.solovgeo.domain.entity.Currency
import com.solovgeo.domain.interactor.CurrencyInteractor
import javax.inject.Inject

class CurrencyListViewModel @Inject constructor(
    private val currencyInteractor: CurrencyInteractor,
    private val currencyListItemFactory: CurrencyListItemFactory
) :
    BaseViewModel() {

    val currencyListItems = MutableLiveData<List<CurrencyListItem>>()

    init {
        initObservers()
    }

    fun changeCurrency(currency: Currency) {
        currencyInteractor.changeCurrency(currency)
    }

    private fun initObservers() {
        currencyInteractor.startSync()
            .observeOnUi()
            .subscribe({ currencyList ->
                val currentCurrencyList = currencyListItems.value
                if (currentCurrencyList == null) {
                    currencyListItems.value = currencyList.rates.map { currencyListItemFactory.create(it.key, it.value) }
                } else {
                    currencyListItems.value = currentCurrencyList.map {
                        it.copy(currencyValue = currencyList.rates.getValue(it.currencyTitle))
                    }
                }
            }, {
                Log.e("CurrencyListViewModel", it.stackTrace.toString())
            })
            .disposeLater()
    }
}
