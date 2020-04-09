package com.solovgeo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_currency_list.*
import toothpick.Toothpick

class CurrencyListActivity : AppCompatActivity() {

    private val currencyListAdapter = CurrencyListAdapter()
    private val viewModel by lazy(LazyThreadSafetyMode.NONE) { createViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_list)
        initRecyclerView()
        initObservers()
    }

    private fun initRecyclerView() {
        rv_activity_main_currency_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@CurrencyListActivity)
            adapter = currencyListAdapter
        }
    }

    private fun initObservers() {
        viewModel.currencyListItems.observe(this, Observer {
            currencyListAdapter.setData(it)
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
