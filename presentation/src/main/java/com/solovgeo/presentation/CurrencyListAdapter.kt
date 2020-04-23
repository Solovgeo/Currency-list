package com.solovgeo.presentation

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
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
        return CurrencyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_currency_list, parent, false))
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        if (holder.name.text != currencies[position].currencyTitle) {
            holder.name.text = currencies[position].currencyTitle
            holder.description.setText(currencies[position].currencyDescriptionRes)
            holder.icon.setImageResource(currencies[position].currencyIconRes)
            if (position == 0) {
                configureFirstItemValue(holder, position)
            }
        }
        if (position != 0) {
            configureOtherItemValue(holder, position)
        }
    }

    override fun getItemCount() = currencies.size

    fun setData(newItems: List<CurrencyListItem>) {
        val diffResult = calculateDiff(this.currencies, newItems)
        this.currencies.clear()
        this.currencies.addAll(newItems)
        diffResult.dispatchUpdatesTo(listUpdateCallback)
    }

    private fun configureFirstItemValue(holder: CurrencyViewHolder, position: Int) {
        holder.editTextValue.apply {
            addTextChangedListener(baseCurrencyListener)
            setText(currencies[position].currencyValue.toFormattedString())
            visibility = View.VISIBLE
            imeOptions = EditorInfo.IME_ACTION_DONE
            requestFocus()
        }
        holder.textViewValue.visibility = View.GONE
        itemEventHandler.showKeyboard(holder.editTextValue)
    }

    private fun configureOtherItemValue(holder: CurrencyViewHolder, position: Int) {
        holder.editTextValue.removeTextChangedListener(baseCurrencyListener)
        holder.textViewValue.text = currencies[position].currencyValue.toFormattedString()
        holder.editTextValue.visibility = View.GONE
        holder.textViewValue.visibility = View.VISIBLE
        holder.view.setOnClickListener {
            itemEventHandler.onItemClick(Currency(currencies[position].currencyTitle, currencies[position].currencyValue))
        }
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

    class CurrencyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.tv_item_currency_list_name
        val description: TextView = view.tv_item_currency_list_description
        val icon: ImageView = view.iv_item_currency_list_icon
        val editTextValue: EditText = view.et_item_currency_list_value
        val textViewValue: TextView = view.tv_item_currency_list_value
    }

    interface ItemEventHandler {
        fun onItemClick(clickedCurrency: Currency)
        fun onValueChange(newCurrency: Currency)
        fun showKeyboard(view: View)
    }
}