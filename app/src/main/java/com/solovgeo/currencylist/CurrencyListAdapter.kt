package com.solovgeo.currencylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_currency_list.view.*

class CurrencyListAdapter() : RecyclerView.Adapter<CurrencyListAdapter.MyViewHolder>() {

    private val currencies = mutableListOf<String>()

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val textView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_currency_list, parent, false)
        return MyViewHolder(textView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.tv_item_currency_list_name.text = currencies[position]
    }

    override fun getItemCount() = currencies.size

    fun setData(currencies: List<String>) {
        this.currencies.clear()
        this.currencies.addAll(currencies)
        notifyDataSetChanged()
    }
}