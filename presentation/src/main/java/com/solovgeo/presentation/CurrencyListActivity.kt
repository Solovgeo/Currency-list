package com.solovgeo.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.solovgeo.domain.entity.Currency
import kotlinx.android.synthetic.main.activity_currency_list.*
import toothpick.Toothpick

class CurrencyListActivity : AppCompatActivity() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) { createViewModel() }
    private val currencyListAdapter = CurrencyListAdapter(object : CurrencyListAdapter.ItemEventHandler {
        override fun onItemClick(clickedCurrency: Currency) {
            Log.d("ActivityLOG onItemClick", clickedCurrency.toString())
            viewModel.selectNewMainCurrency(clickedCurrency)
        }

        override fun onValueChange(newCurrency: Currency) {
            Log.d("ActivityLOG onValueChan", newCurrency.toString())
            viewModel.onMainCurrencyChange(newCurrency)
        }

        override fun showKeyboard(view: View) {
            val imm = this@CurrencyListActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_list)
        initRecyclerView()
        initObservers()
    }

    override fun onStart() {
        super.onStart()
        viewModel.startSync()
    }

    override fun onStop() {
        viewModel.stopSync()
        super.onStop()
    }

    private fun initRecyclerView() {
        rv_activity_main_currency_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@CurrencyListActivity)
            adapter = currencyListAdapter
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

    private fun initObservers() {
        viewModel.currencyListItems.observe(this, Observer {
            currencyListAdapter.setData(it)
        })
        viewModel.scrollToTop.observe(this, Observer {
            if (it) {
                rv_activity_main_currency_list.scrollToPosition(0)
            }
        })
        viewModel.isLoading.observe(this, Observer {
            pb_activity_main_progress.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    private fun createViewModel(): CurrencyListViewModel {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val scope = Toothpick.openScopes(application)
                return scope.getInstance(modelClass)
            }
        }

        return ViewModelProvider(this, factory).get(CurrencyListViewModel::class.java)
    }

}
