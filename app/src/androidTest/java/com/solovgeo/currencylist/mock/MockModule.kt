package com.solovgeo.currencylist.mock

import com.solovgeo.data.network.ServiceApi
import toothpick.config.Module

class MockModule(apiMock: ServiceApi) : Module() {

    init {
        bind(ServiceApi::class.java).toInstance(apiMock)
    }
}