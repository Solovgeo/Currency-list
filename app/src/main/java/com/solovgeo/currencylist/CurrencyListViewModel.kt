package com.solovgeo.currencylist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.solovgeo.core.extentions.observeOnUi
import com.solovgeo.currencylist.base.BaseViewModel
import com.solovgeo.domain.interactor.CurrencyInteractor
import javax.inject.Inject

class CurrencyListViewModel @Inject constructor(private val currencyInteractor: CurrencyInteractor) :
    BaseViewModel() {

    val currencyListItems = MutableLiveData<List<CurrencyListItem>>()

    init {
        loadCurrencies()
    }

    private fun loadCurrencies() {
        currencyInteractor.getCurrencyList()
            .observeOnUi()
            .subscribe({ currencyList ->
                currencyListItems.value = currencyListItems.value!!.map {
                    it.copy(currencyValue = currencyList.rates.getValue(it.currencyTitle))
                }
            }, {
                Log.e("CurrencyListViewModel", it.stackTrace.toString())
            })
            .disposeLater()
    }
}
