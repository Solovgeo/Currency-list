package com.solovgeo.currencylist

import androidx.test.filters.LargeTest
import com.agoda.kakao.screen.Screen
import com.solovgeo.currencylist.base.BaseTestCase
import com.solovgeo.currencylist.mock.ApiMock
import com.solovgeo.currencylist.screens.CurrencyListScreen
import org.junit.Test
import java.math.BigDecimal

@LargeTest
class CurrencyListTests : BaseTestCase() {

    /**
     * Сценарий 1 - Здесь мог быть сценарий написанный тестировщиком
     */
    @Test
    fun testScenario_001() {
        before {
            // Предусловие: валюта по умолчанию - EUR
            // USD = 1.1, RUB = 76.9, CAD = 2.4
            mockAndLaunch(
                apiMock = ApiMock(
                    rates = mapOf("USD" to BigDecimal("1.1"), "RUB" to BigDecimal("76.9"), "CAD" to BigDecimal("2.4"))
                )
            )
        }.after {
        }.run {
            step("""Запустить приложение""") {}
            step("""Проверить что показывается прогрессбар при загрузке""") {
                Screen.onScreen<CurrencyListScreen> {
                    progressBar.isVisible()
                    currencyList.hasSize(0)
                }
            }
            step("""Проверить что через некоторое время прогресс бар пропадет и количество отображаемых валют равно 4""") {
                Screen.onScreen<CurrencyListScreen> {
                    progressBar.isGone()
                    currencyList.hasSize(4)
                }
            }
            step("""Убедиться что первой стоит EUR, остальные валюты также представлены""") {
                Screen.onScreen<CurrencyListScreen> {
                    currencyList.firstChild<CurrencyListScreen.Item> {
                        currencyName.hasText("EUR")
                        currencyDescription.hasText(R.string.eu_description)
                        flagImage.hasDrawable(R.drawable.ic_eu)
                        currencyValueEditText.hasText("1")
                    }
                    currencyList.childAt<CurrencyListScreen.Item>(1) {
                        currencyName.hasText("USD")
                        currencyDescription.hasText(R.string.usd_description)
                        flagImage.hasDrawable(R.drawable.ic_us)
                        currencyValueText.hasText("1.1")
                    }
                    currencyList.childAt<CurrencyListScreen.Item>(2) {
                        currencyName.hasText("RUB")
                        currencyDescription.hasText(R.string.russia_description)
                        flagImage.hasDrawable(R.drawable.ic_russia)
                        currencyValueText.hasText("76.9")
                    }
                    currencyList.childAt<CurrencyListScreen.Item>(3) {
                        currencyName.hasText("CAD")
                        currencyDescription.hasText(R.string.canada_description)
                        flagImage.hasDrawable(R.drawable.ic_canada)
                        currencyValueText.hasText("2.4")
                    }
                }
            }
            step("""Изменить значение у EUR на 10, убедиться что остальные валюты увеличились в 10 раз""") {
                Screen.onScreen<CurrencyListScreen> {
                    currencyList.firstChild<CurrencyListScreen.Item> {
                        currencyValueEditText.replaceText("10")
                    }
                    currencyList.childAt<CurrencyListScreen.Item>(1) {
                        currencyValueText.hasText("11")
                    }
                    currencyList.childAt<CurrencyListScreen.Item>(2) {
                        currencyValueText.hasText("769")
                    }
                    currencyList.childAt<CurrencyListScreen.Item>(3) {
                        currencyValueText.hasText("24")
                    }
                }
            }
            step("""Изменить значение, отдаваемые сервером, убедиться что валюты изменились""") {
                apiMock.rates = mapOf("USD" to BigDecimal("1.11"), "RUB" to BigDecimal("77"), "CAD" to BigDecimal("2.5"))
                Screen.onScreen<CurrencyListScreen> {
                    currencyList.firstChild<CurrencyListScreen.Item> {
                        currencyValueEditText.hasText("10")
                    }
                    currencyList.childAt<CurrencyListScreen.Item>(1) {
                        currencyValueText.hasText("11.1")
                    }
                    currencyList.childAt<CurrencyListScreen.Item>(2) {
                        currencyValueText.hasText("770")
                    }
                    currencyList.childAt<CurrencyListScreen.Item>(3) {
                        currencyValueText.hasText("25")
                    }
                }
            }
            step("""Кликнуть на CAD, убедиться что CAD переместился вверх списка""") {
                Screen.onScreen<CurrencyListScreen> {
                    currencyList.childAt<CurrencyListScreen.Item>(3) {
                        click()
                    }
                    apiMock.rates = mapOf("USD" to BigDecimal("0.5"), "RUB" to BigDecimal("46"), "EUR" to BigDecimal("1.05"))
                    currencyList.childAt<CurrencyListScreen.Item>(0) {
                        currencyName.hasText("CAD")
                    }
                    currencyList.childAt<CurrencyListScreen.Item>(1) {
                        currencyName.hasText("EUR")
                    }
                    currencyList.childAt<CurrencyListScreen.Item>(2) {
                        currencyName.hasText("USD")
                    }
                    currencyList.childAt<CurrencyListScreen.Item>(3) {
                        currencyName.hasText("RUB")
                    }
                }
            }
            step("""Изменить значение у CAD, убедиться что валюты меняются вместе с новым значением, при этом значение CAD меняется только пользователем""") {
                Screen.onScreen<CurrencyListScreen> {
                    currencyList.firstChild<CurrencyListScreen.Item> {
                        currencyValueEditText.replaceText("5")
                    }
                    currencyList.childAt<CurrencyListScreen.Item>(1) {
                        currencyValueText.hasText("5.25")
                    }
                    currencyList.childAt<CurrencyListScreen.Item>(2) {
                        currencyValueText.hasText("2.5")
                    }
                    currencyList.childAt<CurrencyListScreen.Item>(3) {
                        currencyValueText.hasText("230")
                    }
                }
            }

        }
    }
}