package com.solovgeo.currencylist.base

import com.agoda.kakao.screen.Screen
import kotlin.reflect.KClass

abstract class BaseScreen<out T : Screen<T>> : Screen<T>() {
    abstract val layoutId: Int
    abstract val viewClass: KClass<*>
}