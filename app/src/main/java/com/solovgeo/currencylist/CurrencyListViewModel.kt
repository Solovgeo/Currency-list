package com.solovgeo.currencylist

import android.util.Log
import com.solovgeo.core.extentions.observeOnUi
import com.solovgeo.currencylist.base.BaseViewModel
import com.solovgeo.domain.interactor.CurrencyInteractor
import javax.inject.Inject

class CurrencyListViewModel @Inject constructor(private val currencyInteractor: CurrencyInteractor) :
    BaseViewModel() {

    init {
        loadCurrencies()
    }

    private fun loadCurrencies() {
        currencyInteractor.getCurrencyList()
            .observeOnUi()
            .subscribe({

            }, {
                Log.e("CurrencyListViewModel", it.stackTrace.toString())
            })
            .disposeLater()
    }
}
