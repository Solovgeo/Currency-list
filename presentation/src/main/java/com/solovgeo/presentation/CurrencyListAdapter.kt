package com.solovgeo.presentation

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.RecyclerView
import com.solovgeo.domain.entity.Currency
import com.solovgeo.presentation.base.NotifyFirstItemChangeAdapterListUpdateCallback
import com.solovgeo.presentation.extentions.toFormattedString
import kotlinx.android.synthetic.main.item_currency_list.view.*
import java.math.BigDecimal

class CurrencyListAdapter(private val itemEventHandler: ItemEventHandler) : RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder>() {

    private val currencies = mutableListOf<CurrencyListItem>()
    private val listUpdateCallback = NotifyFirstItemChangeAdapterListUpdateCallback(this)
    private val baseCurrencyListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            val string = s.toString()
            val value = if (string.isEmpty()) {
                BigDecimal.ZERO
            } else {
                string.toBigDecimal()
            }
            itemEventHandler.onValueChange(Currency(currencies[0].currencyTitle, value))
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(R.layout.item_currency_list, parent, false)
        return CurrencyViewHolder(textView)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.view.tv_item_currency_list_name.text = currencies[position].currencyTitle
        holder.view.tv_item_currency_list_description.setText(currencies[position].currencyDescriptionRes)
        holder.view.iv_item_currency_list_icon.setImageResource(currencies[position].currencyIconRes)
        holder.view.et_item_currency_list_value.setText(currencies[position].currencyValue.toFormattedString())

        val onClickListener = View.OnClickListener {
            itemEventHandler.onItemClick(Currency(currencies[position].currencyTitle, currencies[position].currencyValue))
        }
        holder.view.setOnClickListener(onClickListener)
        if (position == 0) {
            holder.view.et_item_currency_list_value.addTextChangedListener(baseCurrencyListener)
            holder.view.et_item_currency_list_value.requestFocus()
        } else {
            holder.view.et_item_currency_list_value.removeTextChangedListener(baseCurrencyListener)
        }
    }

    override fun getItemCount() = currencies.size

    fun setData(newItems: List<CurrencyListItem>) {
        val diffResult = calculateDiff(this.currencies, newItems)
        this.currencies.clear()
        this.currencies.addAll(newItems)
        diffResult.dispatchUpdatesTo(listUpdateCallback)
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

    class CurrencyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    interface ItemEventHandler {
        fun onItemClick(clickedCurrency: Currency)
        fun onValueChange(newCurrency: Currency)
    }
}