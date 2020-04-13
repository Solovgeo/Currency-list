package com.solovgeo.currencylist.screens

import android.view.View
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.progress.KProgressBar
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.text.KTextView
import com.solovgeo.currencylist.R
import com.solovgeo.currencylist.base.BaseScreen
import com.solovgeo.presentation.CurrencyListActivity
import org.hamcrest.Matcher

class CurrencyListScreen : BaseScreen<CurrencyListScreen>() {
    override val layoutId = R.layout.activity_currency_list
    override val viewClass = CurrencyListActivity::class

    val mainTitle = KTextView { withId(R.id.tv_activity_main_title) }
    val progressBar = KProgressBar { withId(R.id.pb_activity_main_progress) }
    val currencyList = KRecyclerView(builder = { withId(R.id.rv_activity_main_currency_list) }, itemTypeBuilder = { itemType(::Item) })

    class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        val flagImage = KImageView(parent) { withId(R.id.iv_item_currency_list_icon) }
        val currencyName = KTextView(parent) { withId(R.id.tv_item_currency_list_name) }
        val currencyDescription = KTextView(parent) { withId(R.id.tv_item_currency_list_description) }
        val currencyValueEditText = KEditText(parent) { withId(R.id.et_item_currency_list_value) }
        val currencyValueText = KTextView(parent) { withId(R.id.tv_item_currency_list_value) }
    }
}