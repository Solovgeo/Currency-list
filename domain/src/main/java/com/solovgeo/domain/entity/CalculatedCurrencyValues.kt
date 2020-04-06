package com.solovgeo.domain.entity

import java.math.BigDecimal

data class CalculatedCurrencyValues(
    val rates: Map<String, BigDecimal>
)