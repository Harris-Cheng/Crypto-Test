package com.example.domain

import app.cash.turbine.test
import com.example.domain.model.CurrencyModel
import com.example.domain.usecase.FilterCurrencyTypeUseCaseImpl
import com.example.domain.usecase.IFilterCurrencyTypeUseCase
import com.example.domain.usecase.IGetCurrencyListUseCase
import com.example.domain.usecase.ListType
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FilterCurrencyListUseCaseTest {
    private lateinit var filterCurrencyUseCase: IFilterCurrencyTypeUseCase

    @MockK
    private lateinit var getCurrencyListUseCase: IGetCurrencyListUseCase

    private val mockCoinList = listOf(
        CurrencyModel.Coin(
            "C1",
            "Coin 1",
            "C1"
        ),
        CurrencyModel.Coin(
            "C2",
            "Coin 2",
            "C2"
        ),
    )

    private val mockFiatList = listOf(
        CurrencyModel.Fiat(
            "F1",
            "Fiat 1",
            "F1"
        ),
        CurrencyModel.Fiat(
            "F2",
            "Fiat 2",
            "F2"
        ),
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        filterCurrencyUseCase = FilterCurrencyTypeUseCaseImpl()

        every {
            getCurrencyListUseCase.execute()
        } returns flowOf(mockCoinList + mockFiatList)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `filter with type all success`() = runTest {
        getCurrencyListUseCase.execute().test {
            val result = filterCurrencyUseCase.execute(
                awaitItem(),
                ListType.All
            )

            assertEquals(
                mockCoinList + mockFiatList,
                result
            )

            awaitComplete()
        }
    }

    @Test
    fun `filter with coin type success`() = runTest {
        getCurrencyListUseCase.execute().test {
            val result = filterCurrencyUseCase.execute(
                awaitItem(),
                ListType.Coin
            )

            assertEquals(
                mockCoinList,
                result
            )

            awaitComplete()
        }
    }

    @Test
    fun `filter with fiat type success`() = runTest {
        getCurrencyListUseCase.execute().test {
            val result = filterCurrencyUseCase.execute(
                awaitItem(),
                ListType.Fiat
            )

            assertEquals(
                mockFiatList,
                result
            )

            awaitComplete()
        }
    }
}