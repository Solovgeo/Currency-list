package com.solovgeo.domain.entity

import java.math.BigDecimal

data class CurrencyListCalculated(
    val baseCurrency: Currency,
    val rates: Map<String, BigDecimal>
)