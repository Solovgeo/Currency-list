package com.solovgeo.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.solovgeo.core.extentions.observeOnUi
import com.solovgeo.domain.entity.Currency
import com.solovgeo.domain.entity.CurrencyListCalculated
import com.solovgeo.domain.interactor.CurrencyInteractor
import com.solovgeo.presentation.base.BaseViewModel
import com.solovgeo.presentation.base.SingleLiveEvent
import javax.inject.Inject

class CurrencyListViewModel @Inject constructor(
    private val currencyInteractor: CurrencyInteractor,
    private val currencyListItemFactory: CurrencyListItemFactory
) : BaseViewModel() {

    val currencyListItems = MutableLiveData<List<CurrencyListItem>>()
    val scrollToTop = SingleLiveEvent<Boolean>()

    init {
        initObservers()
    }

    private fun initObservers() {
        currencyInteractor.startSync()
            .observeOnUi()
            .subscribe(::processCurrencyList) {
                Log.e("ERROR", "CurrencyListViewModel", it)
            }
            .disposeLater()
    }

    private fun processCurrencyList(currencyList: CurrencyListCalculated) {
        val currentCurrencyList = currencyListItems.value
        if (currentCurrencyList == null) {
            val newValue = mutableListOf<CurrencyListItem>()
            newValue.add(currencyListItemFactory.create(currencyList.baseCurrency.name, currencyList.baseCurrency.value))
            newValue.addAll(currencyList.rates.map { currencyListItemFactory.create(it.key, it.value) })
            currencyListItems.value = newValue
        } else {
            currencyListItems.value = currentCurrencyList.map { currencyListItem ->
                currencyList.rates[currencyListItem.currencyTitle]?.let { currencyListItem.copy(currencyValue = it) } ?: currencyListItem
            }
        }
    }

    fun onValueChange(newCurrency: Currency) {
        currencyInteractor.changeCurrency(newCurrency)
    }

    fun selectNewMainCurrency(clickedCurrency: Currency) {
        val currentCurrencyList = currencyListItems.value
        currencyListItems.value = currentCurrencyList?.toMutableList()?.apply {
            val item = find { it.currencyTitle == clickedCurrency.name }
            remove(item)
            item?.let { add(0, it) }
        }
        scrollToTop.value = true
        currencyInteractor.changeCurrency(clickedCurrency)
    }
}
