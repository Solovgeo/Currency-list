package com.solovgeo.presentation

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.only
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.solovgeo.base.BaseTest
import com.solovgeo.domain.entity.Currency
import com.solovgeo.domain.entity.CurrencyListCalculated
import com.solovgeo.domain.interactor.CurrencyInteractor
import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.PublishSubject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.math.BigDecimal

class CurrencyListViewModelTest : BaseTest() {

    @Mock
    lateinit var currencyInteractor: CurrencyInteractor
    @Mock
    lateinit var currencyListItemFactory: CurrencyListItemFactory

    private lateinit var viewModel: CurrencyListViewModel
    private val subject = PublishSubject.create<CurrencyListCalculated>()
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = CurrencyListViewModel(currencyInteractor = currencyInteractor, currencyListItemFactory = currencyListItemFactory)

        whenever(currencyInteractor.startSync()).thenReturn(subject.toFlowable(BackpressureStrategy.BUFFER))
        whenever(currencyListItemFactory.create(any(), any())).thenAnswer {
            CurrencyListItem(
                currencyIconRes = 0,
                currencyTitle = it.getArgument(0, String::class.java),
                currencyDescriptionRes = 0,
                currencyValue = it.getArgument(1, BigDecimal::class.java)
            )
        }
    }

    @Test
    fun `test sync updates`() {
        viewModel.startSync()
        subject.onNext(
            CurrencyListCalculated(
                baseCurrency = Currency(name = "EUR", value = BigDecimal.ONE),
                rates = mapOf("USD" to BigDecimal("1.1"), "RUB" to BigDecimal("76.9"))
            )
        )
        var currencyListItems = viewModel.currencyListItems.value!!

        assertEquals(3, currencyListItems.size)
        assertEquals("EUR", currencyListItems[0].currencyTitle)
        assertTrue(currencyListItems[0].currencyValue.compareTo(BigDecimal.ONE) == 0)
        assertEquals("USD", currencyListItems[1].currencyTitle)
        assertTrue(currencyListItems[1].currencyValue.compareTo(BigDecimal("1.1")) == 0)
        assertEquals("RUB", currencyListItems[2].currencyTitle)
        assertTrue(currencyListItems[2].currencyValue.compareTo(BigDecimal("76.9")) == 0)

        subject.onNext(
            CurrencyListCalculated(
                baseCurrency = Currency(name = "EUR", value = BigDecimal.ONE),
                rates = mapOf("USD" to BigDecimal("1.15"), "RUB" to BigDecimal("76.8"))
            )
        )
        currencyListItems = viewModel.currencyListItems.value!!

        assertEquals(3, currencyListItems.size)
        assertEquals("EUR", currencyListItems[0].currencyTitle)
        assertTrue(currencyListItems[0].currencyValue.compareTo(BigDecimal.ONE) == 0)
        assertEquals("USD", currencyListItems[1].currencyTitle)
        assertTrue(currencyListItems[1].currencyValue.compareTo(BigDecimal("1.15")) == 0)
        assertEquals("RUB", currencyListItems[2].currencyTitle)
        assertTrue(currencyListItems[2].currencyValue.compareTo(BigDecimal("76.8")) == 0)
    }

    @Test
    fun `test pass value on main currency change`() {
        viewModel.onMainCurrencyChange(Currency("EUR", BigDecimal("1.15")))
        verify(currencyInteractor, only()).changeCurrency(Currency("EUR", BigDecimal("1.15")))
    }

    @Test
    fun `test swap currencies on selectNewMainCurrency`() {
        viewModel.startSync()
        subject.onNext(
            CurrencyListCalculated(
                baseCurrency = Currency(name = "EUR", value = BigDecimal.ONE),
                rates = mapOf("USD" to BigDecimal("1.15"), "RUB" to BigDecimal("76.9"))
            )
        )
        var currencyListItems = viewModel.currencyListItems.value!!
        assertEquals("EUR", currencyListItems[0].currencyTitle)
        assertTrue(currencyListItems[0].currencyValue.compareTo(BigDecimal.ONE) == 0)
        assertEquals("USD", currencyListItems[1].currencyTitle)
        assertTrue(currencyListItems[1].currencyValue.compareTo(BigDecimal("1.15")) == 0)
        assertEquals("RUB", currencyListItems[2].currencyTitle)
        assertTrue(currencyListItems[2].currencyValue.compareTo(BigDecimal("76.9")) == 0)

        viewModel.selectNewMainCurrency(Currency("RUB", BigDecimal("76.9")))
        currencyListItems = viewModel.currencyListItems.value!!
        assertEquals("RUB", currencyListItems[0].currencyTitle)
        assertTrue(currencyListItems[0].currencyValue.compareTo(BigDecimal("76.9")) == 0)
        assertEquals("EUR", currencyListItems[1].currencyTitle)
        assertTrue(currencyListItems[1].currencyValue.compareTo(BigDecimal.ONE) == 0)
        assertEquals("USD", currencyListItems[2].currencyTitle)
        assertTrue(currencyListItems[2].currencyValue.compareTo(BigDecimal("1.15")) == 0)
    }
}