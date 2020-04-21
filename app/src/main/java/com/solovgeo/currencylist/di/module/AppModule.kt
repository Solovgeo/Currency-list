package com.solovgeo.currencylist.di.module

import com.google.gson.Gson
import com.solovgeo.currencylist.di.provider.GsonProvider
import com.solovgeo.currencylist.di.provider.ServiceApiProvider
import com.solovgeo.currencylist.di.provider.ServiceHttpClientProvider
import com.solovgeo.currencylist.di.qualifier.ServiceHttpClient
import com.solovgeo.data.network.ServiceApi
import com.solovgeo.data.repository.DefaultCurrencyRepository
import com.solovgeo.domain.interactor.CurrencyInteractor
import com.solovgeo.domain.repository.CurrencyRepository
import okhttp3.OkHttpClient
import toothpick.config.Module

class AppModule : Module() {
    init {

        // Gson
        bind(Gson::class.java).toProvider(GsonProvider::class.java).providesSingleton()

        // OkHttp
        bind(OkHttpClient::class.java).withName(ServiceHttpClient::class.java).toProvider(ServiceHttpClientProvider::class.java).providesSingleton()

        // Retrofit
        bind(ServiceApi::class.java).toProvider(ServiceApiProvider::class.java).providesSingleton()

        // Repositories
        bind(CurrencyRepository::class.java).to(DefaultCurrencyRepository::class.java).singleton()

        // Interactors
        bind(CurrencyInteractor::class.java).singleton()
    }
}