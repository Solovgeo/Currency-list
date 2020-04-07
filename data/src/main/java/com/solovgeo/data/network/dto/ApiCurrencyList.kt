package com.solovgeo.data.network.dto

import java.math.BigDecimal

data class ApiCurrencyList(
    val baseCurrency: String,
    val rates: Map<String, BigDecimal>
)