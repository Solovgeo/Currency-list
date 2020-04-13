package com.solovgeo.currencylist.base

import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.ActivityTestRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.solovgeo.currencylist.App
import com.solovgeo.currencylist.mock.ApiMock
import com.solovgeo.currencylist.mock.MockModule
import com.solovgeo.presentation.CurrencyListActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import toothpick.Scope
import toothpick.Toothpick

open class BaseTestCase : TestCase() {

    private lateinit var application: App
    private lateinit var appScope: Scope
    protected lateinit var apiMock: ApiMock
    @Rule
    @JvmField
    var mActivityTestRule: ActivityTestRule<CurrencyListActivity> =
        object : ActivityTestRule<CurrencyListActivity>(CurrencyListActivity::class.java, false, false) {

        }

    @Before
    fun setup() {
        application = ApplicationProvider.getApplicationContext() as App
        appScope = Toothpick.openScope(application)
        Toothpick.reset(appScope)
        application.installToothpickModules(appScope)
    }

    @After
    fun tearDown() {
        Toothpick.reset(appScope)
        application.installToothpickModules(appScope)
    }

    protected fun mockAndLaunch(
        apiMock: ApiMock = ApiMock()
    ) {
        this@BaseTestCase.apiMock = apiMock
        appScope.installTestModules(MockModule(apiMock = apiMock))
        mActivityTestRule.launchActivity(null)
    }
}