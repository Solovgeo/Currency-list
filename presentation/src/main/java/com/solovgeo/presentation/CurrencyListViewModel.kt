package com.solovgeo.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solovgeo.core.extentions.observeOnUi
import com.solovgeo.domain.entity.Currency
import com.solovgeo.domain.entity.CurrencyListCalculated
import com.solovgeo.domain.interactor.CurrencyInteractor
import com.solovgeo.presentation.base.SingleLiveEvent
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class CurrencyListViewModel @Inject constructor(
    private val currencyInteractor: CurrencyInteractor,
    private val currencyListItemFactory: CurrencyListItemFactory
) : ViewModel() {

    val currencyListItems = MutableLiveData<List<CurrencyListItem>>()
    val scrollToTop = SingleLiveEvent<Boolean>()
    val isLoading = MutableLiveData<Boolean>().apply { value = true }
    private var syncDisposable: Disposable? = null

    init {
        syncDisposable = currencyInteractor.startSync()
            .map(::processCurrencyList)
            .observeOnUi()
            .subscribe({
                if (isLoading.value != false) {
                    isLoading.value = false
                }
                currencyListItems.value = it
            }) {
                Log.e("ERROR", "CurrencyListViewModel", it)
            }
    }

    fun onMainCurrencyChange(newCurrency: Currency) {
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

    override fun onCleared() {
        syncDisposable?.dispose()
        super.onCleared()
    }

    private fun processCurrencyList(currencyList: CurrencyListCalculated): List<CurrencyListItem> {
        val currentCurrencyList = currencyListItems.value
        return if (currentCurrencyList == null) {
            createNewList(currencyList)
        } else {
            updateCurrentList(currentCurrencyList, currencyList)
        }
    }

    private fun createNewList(currencyList: CurrencyListCalculated): List<CurrencyListItem> {
        val newValue = mutableListOf<CurrencyListItem>()
        newValue.add(currencyListItemFactory.create(currencyList.baseCurrency.name, currencyList.baseCurrency.value))
        newValue.addAll(currencyList.rates.map { currencyListItemFactory.create(it.key, it.value) })
        return newValue
    }

    private fun updateCurrentList(currentCurrencyList: List<CurrencyListItem>, currencyList: CurrencyListCalculated): List<CurrencyListItem> {
        return currentCurrencyList.map { currencyListItem ->
            currencyList.rates[currencyListItem.currencyTitle]?.let { currencyListItem.copy(currencyValue = it) } ?: currencyListItem
        }
    }

}
