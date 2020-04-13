package com.solovgeo.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.rules.TestRule

abstract class BaseTest {
    @Rule
    @JvmField
    var overrideSchedulersRule = RxSchedulersOverrideRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
}
