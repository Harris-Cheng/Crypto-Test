package com.example.domain

import app.cash.turbine.test
import com.example.domain.model.CurrencyModel
import com.example.domain.usecase.IGetCurrencyListUseCase
import com.example.domain.usecase.ISearchCurrencyUseCase
import com.example.domain.usecase.SearchCurrencyUseCaseImpl
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

class SearchCurrencyUseCaseTest {
    private lateinit var useCase: ISearchCurrencyUseCase

    @MockK
    private lateinit var getCurrencyListUseCase: IGetCurrencyListUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        useCase = SearchCurrencyUseCaseImpl()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `search coin with name that starts with search term success`() = runTest {
        every {
            getCurrencyListUseCase.execute()
        } returns flowOf(
            listOf(
                CurrencyModelUtil.createCoinWithName("Foobar"),
                CurrencyModelUtil.createCoinWithName("Barfoo"),
            )
        )

        getCurrencyListUseCase.execute().test {
            val result = useCase.execute(
                awaitItem(),
                "foo"
            )

            assertEquals(
                listOf(
                    CurrencyModelUtil.createCoinWithName("Foobar"),
                ),
                result
            )

            awaitComplete()
        }
    }

    @Test
    fun `search coin with name that starts with whole word success`() = runTest {
        every {
            getCurrencyListUseCase.execute()
        } returns flowOf(
            listOf(
                CurrencyModelUtil.createCoinWithName("Ethereum"),
                CurrencyModelUtil.createCoinWithName("Ethereum Classic"),
            )
        )

        getCurrencyListUseCase.execute().test {
            val result = useCase.execute(
                awaitItem(),
                "Ethereum"
            )

            assertEquals(
                listOf(
                    CurrencyModelUtil.createCoinWithName("Ethereum"),
                    CurrencyModelUtil.createCoinWithName("Ethereum Classic"),
                ),
                result
            )

            awaitComplete()
        }
    }

    @Test
    fun `search coin with name that starts with search term fail`() = runTest {
        every {
            getCurrencyListUseCase.execute()
        } returns flowOf(
            listOf(
                CurrencyModelUtil.createCoinWithName("Foobar"),
                CurrencyModelUtil.createCoinWithName("Barfoo"),
            )
        )

        getCurrencyListUseCase.execute().test {
            val result = useCase.execute(
                awaitItem(),
                "oo"
            )

            assertEquals(
                emptyList<CurrencyModel>(),
                result
            )

            awaitComplete()
        }
    }

    @Test
    fun `search coin with name that contains partial match with space prefixed success`() = runTest {
        every {
            getCurrencyListUseCase.execute()
        } returns flowOf(
            listOf(
                CurrencyModelUtil.createCoinWithName("Ethereum Classic"),
                CurrencyModelUtil.createCoinWithName("TronClassic"),
            )
        )

        getCurrencyListUseCase.execute().test {
            val result = useCase.execute(
                awaitItem(),
                "Classic"
            )

            assertEquals(
                listOf(
                    CurrencyModelUtil.createCoinWithName("Ethereum Classic"),
                ),
                result
            )

            awaitComplete()
        }
    }

    @Test
    fun `search coin with name that contains partial match with space prefixed fail`() = runTest {
        every {
            getCurrencyListUseCase.execute()
        } returns flowOf(
            listOf(
                CurrencyModelUtil.createCoinWithName("EthereumClassic"),
                CurrencyModelUtil.createCoinWithName("TronClassic"),
            )
        )

        getCurrencyListUseCase.execute().test {
            val result = useCase.execute(
                awaitItem(),
                "Classic"
            )

            assertEquals(
                emptyList<CurrencyModel>(),
                result
            )

            awaitComplete()
        }
    }

    @Test
    fun `search coin with symbol that starts with search term success`() = runTest {
        every {
            getCurrencyListUseCase.execute()
        } returns flowOf(
            listOf(
                CurrencyModelUtil.createCoinWithSymbol("ETC"),
                CurrencyModelUtil.createCoinWithSymbol("ETH"),
                CurrencyModelUtil.createCoinWithSymbol("BTC"),
            )
        )

        getCurrencyListUseCase.execute().test {
            val result = useCase.execute(
                awaitItem(),
                "ET"
            )

            assertEquals(
                listOf(
                    CurrencyModelUtil.createCoinWithSymbol("ETC"),
                    CurrencyModelUtil.createCoinWithSymbol("ETH"),
                ),
                result
            )

            awaitComplete()
        }
    }

    @Test
    fun `search coin with symbol that starts with search term fail`() = runTest {
        every {
            getCurrencyListUseCase.execute()
        } returns flowOf(
            listOf(
                CurrencyModelUtil.createCoinWithSymbol("ABC"),
                CurrencyModelUtil.createCoinWithSymbol("BBC"),
                CurrencyModelUtil.createCoinWithSymbol("CBC"),
            )
        )

        getCurrencyListUseCase.execute().test {
            val result = useCase.execute(
                awaitItem(),
                "BC"
            )

            assertEquals(
                emptyList<CurrencyModel>(),
                result
            )

            awaitComplete()
        }
    }
}