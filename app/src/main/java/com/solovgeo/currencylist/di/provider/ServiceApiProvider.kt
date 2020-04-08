package com.solovgeo.currencylist.di.provider

import com.google.gson.Gson
import com.solovgeo.currencylist.BuildConfig
import com.solovgeo.currencylist.di.qualifier.ServiceHttpClient
import com.solovgeo.data.network.ServiceApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Provider

class ServiceApiProvider @Inject constructor(
    private val gson: Gson,
    @ServiceHttpClient private val okHttpClient: OkHttpClient
) : Provider<ServiceApi> {
    override fun get(): ServiceApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(ServiceApi::class.java)
    }
}