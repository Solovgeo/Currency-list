package com.solovgeo.data.network.dto

data class ApiCurrencyList(
    val baseCurrency: String,
    val rates: HashMap<String, Double>
)