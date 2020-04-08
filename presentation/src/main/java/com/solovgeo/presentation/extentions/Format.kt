package com.solovgeo.presentation.extentions

import java.math.BigDecimal
import java.text.DecimalFormat

object Formatters {
    val scaleTwoFormat = DecimalFormat("#0.00######")
}

fun BigDecimal.toFormattedString(): String {
    return Formatters.scaleTwoFormat.format(this)
}