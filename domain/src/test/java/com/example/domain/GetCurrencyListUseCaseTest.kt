package com.example.domain

import app.cash.turbine.test
import com.example.data.coin.model.Coin
import com.example.data.coin.repository.ICurrencyListRepository
import com.example.domain.model.CurrencyModel
import com.example.domain.usecase.GetCurrencyListUseCaseImpl
import com.example.domain.usecase.IGetCurrencyListUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class GetCurrencyListUseCaseTest {
    private lateinit var getCurrencyListUseCase: IGetCurrencyListUseCase

    @MockK
    private lateinit var repository: ICurrencyListRepository

    private val mockCurrencyList = listOf(
        Coin(0, "C1", "Coin 1", "C1", ""),
        Coin(1, "C2", "Coin 2", "C2", ""),
        Coin(2, "F1", "Fiat 1", "F1", "F1"),
        Coin(3, "F2", "Fiat 2", "F2", "F2"),
    )

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

        getCurrencyListUseCase = GetCurrencyListUseCaseImpl(
            repository
        )

        every {
            repository.getCurrencyListFlow()
        } returns flowOf(mockCurrencyList)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `get full currency list success`() = runTest {
        getCurrencyListUseCase.execute().test {
            assertEquals(
                mockCoinList + mockFiatList,
                awaitItem()
            )
            awaitComplete()
        }
    }
}