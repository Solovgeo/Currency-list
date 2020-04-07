package com.solovgeo.data.network

import com.solovgeo.data.network.dto.ApiCurrencyList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {
    @GET("android/latest")
    fun getCurrencyList(@Query("base") baseCurrency: String?): Single<ApiCurrencyList>
}