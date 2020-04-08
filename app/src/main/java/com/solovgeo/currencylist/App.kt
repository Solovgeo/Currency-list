package com.solovgeo.currencylist

import android.app.Application
import com.solovgeo.currencylist.di.module.AppModule
import toothpick.Toothpick

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initToothpick()
    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(toothpick.configuration.Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(toothpick.configuration.Configuration.forProduction())
        }

        val appScope = Toothpick.openScope(this)
        appScope.installModules(AppModule())
    }
}