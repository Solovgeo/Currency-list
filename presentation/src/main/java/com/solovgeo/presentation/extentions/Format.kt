package com.solovgeo.presentation.extentions

import java.math.BigDecimal
import java.text.DecimalFormat

object Formatters {
    val decimalFormat = DecimalFormat("#0.######")
}

fun BigDecimal.toFormattedString(): String {
    return Formatters.decimalFormat.format(this)
}