package com.solovgeo.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.RecyclerView
import com.solovgeo.presentation.extentions.toFormattedString
import kotlinx.android.synthetic.main.item_currency_list.view.*

class CurrencyListAdapter() : RecyclerView.Adapter<CurrencyListAdapter.MyViewHolder>() {

    private val currencies = mutableListOf<CurrencyListItem>()

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(R.layout.item_currency_list, parent, false)
        return MyViewHolder(textView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.tv_item_currency_list_name.text = currencies[position].currencyTitle
        holder.view.tv_item_currency_list_description.setText(currencies[position].currencyDescriptionRes)
        holder.view.iv_item_currency_list_icon.setImageResource(currencies[position].currencyIconRes)
        holder.view.et_item_currency_list_value.setText(currencies[position].currencyValue.toFormattedString())
    }

    override fun getItemCount() = currencies.size

    fun setData(newItems: List<CurrencyListItem>) {
        val diffResult = calculateDiff(this.currencies, newItems)
        this.currencies.clear()
        this.currencies.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun calculateDiff(oldItems: List<CurrencyListItem>, newItems: List<CurrencyListItem>): DiffResult {
        return DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldItems.size
            }

            override fun getNewListSize(): Int {
                return newItems.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = oldItems[oldItemPosition]
                val newItem = newItems[newItemPosition]
                return oldItem.currencyTitle == newItem.currencyTitle
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = oldItems[oldItemPosition]
                val newItem = newItems[newItemPosition]
                return oldItem == newItem
            }
        })
    }
}