package com.solovgeo.domain.entity

import java.math.BigDecimal

data class CurrencyList(
    val baseCurrency: String,
    val rates: Map<String, BigDecimal>
)