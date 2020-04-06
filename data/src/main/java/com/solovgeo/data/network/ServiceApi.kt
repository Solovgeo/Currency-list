package com.solovgeo.data.network

import com.solovgeo.data.network.dto.ApiCurrencyList
import io.reactivex.Single
import retrofit2.http.GET

interface ServiceApi {
    @GET("android/latest")
    fun getCurrencyList(): Single<ApiCurrencyList>
}