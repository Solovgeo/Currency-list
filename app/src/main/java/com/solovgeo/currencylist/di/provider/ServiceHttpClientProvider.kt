package com.solovgeo.currencylist.di.provider

import com.solovgeo.currencylist.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

class ServiceHttpClientProvider @Inject constructor() : Provider<OkHttpClient> {

    override fun get(): OkHttpClient {
        return with(OkHttpClient.Builder()) {
            callTimeout(10, TimeUnit.SECONDS)
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

                addInterceptor(httpLoggingInterceptor)
            }

            build()
        }
    }
}