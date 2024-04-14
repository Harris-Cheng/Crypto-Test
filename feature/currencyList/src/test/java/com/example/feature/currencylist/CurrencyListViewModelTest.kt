package com.example.feature.currencylist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.TurbineTestContext
import app.cash.turbine.test
import com.example.common.utils.AppCoroutineDispatcher
import com.example.domain.model.CurrencyModel
import com.example.domain.usecase.IDeleteCurrencyListUseCase
import com.example.domain.usecase.IFilterCurrencyTypeUseCase
import com.example.domain.usecase.IGetCurrencyListUseCase
import com.example.domain.usecase.IInsertCurrencyListUseCase
import com.example.domain.usecase.ISearchCurrencyUseCase
import com.example.feature.currencylist.viewmodel.CurrencyListViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyListViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getCurrencyListUseCase: IGetCurrencyListUseCase

    @MockK
    private lateinit var insertCurrencyListUseCase: IInsertCurrencyListUseCase

    @MockK
    private lateinit var deleteCurrencyListUseCase: IDeleteCurrencyListUseCase

    @MockK
    private lateinit var filterCurrencyListUseCase: IFilterCurrencyTypeUseCase

    @MockK
    private lateinit var searchCurrencyUseCase: ISearchCurrencyUseCase

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var appCoroutineDispatcher: AppCoroutineDispatcher

    private lateinit var viewModel: CurrencyListViewModel

    private val mockCoinList = listOf(
        CurrencyModel.Coin(
            shortName = "BTC",
            name = "Bitcoin",
            "BTC",
        ),
        CurrencyModel.Coin(
            shortName = "ETH",
            name = "Ethereum",
            "ETH",
        ),
    )

    private val mockFiatList = listOf(
        CurrencyModel.Fiat(
            shortName = "SGD",
            name = "Singapore Dollar",
            "$",
        ),
        CurrencyModel.Fiat(
            shortName = "HKD",
            name = "Hong Kong Dollar",
            "$",
        ),
    )

    private val mockList = mockCoinList + mockFiatList

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        every {
            getCurrencyListUseCase.execute()
        } returns flowOf(mockList)
        coEvery {
            filterCurrencyListUseCase.execute(any(), any())
        } returns mockList
        every {
            searchCurrencyUseCase.execute(any(), any())
        } returns mockList
        coJustRun {
            insertCurrencyListUseCase.execute()
        }

        appCoroutineDispatcher = AppCoroutineDispatcher(
            testDispatcher,
            testDispatcher,
            testDispatcher
        )

        viewModel = CurrencyListViewModel(
            appCoroutineDispatcher,
            getCurrencyListUseCase,
            insertCurrencyListUseCase,
            deleteCurrencyListUseCase,
            filterCurrencyListUseCase,
            searchCurrencyUseCase
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private suspend fun TurbineTestContext<List<CurrencyModel>>.consumeInitialEmit() {
        // consume initial full list item emitted
        assertEquals(awaitItem(), mockList)
    }

    @Test
    fun `get full currency list success`() = runTest {
        viewModel.displayList.test {
            val result = awaitItem()
            assertEquals(result, mockList)
        }
    }

    @Test
    fun `get Coin List after tapping show list A`() = runTest{
        viewModel.displayList.test {
            consumeInitialEmit()

            coEvery {
                filterCurrencyListUseCase.execute(any(), any())
            } returns mockCoinList

            viewModel.onShowListAClick()

            assertEquals(awaitItem(), mockCoinList)
        }
    }

    @Test
    fun `get Fiat List after tapping show list B`() = runTest{
        viewModel.displayList.test {
            consumeInitialEmit()

            coEvery {
                filterCurrencyListUseCase.execute(any(), any())
            } returns mockFiatList

            viewModel.onShowListBClick()

            assertEquals(awaitItem(), mockFiatList)
        }
    }

    @Test
    fun `switch filter type from coin to fiat to all`() = runTest {
        viewModel.displayList.test {
            consumeInitialEmit()

            // coin list
            coEvery {
                filterCurrencyListUseCase.execute(any(), any())
            } returns mockCoinList
            viewModel.onShowListAClick()
            assertEquals(awaitItem(), mockCoinList)

            // fiat list
            coEvery {
                filterCurrencyListUseCase.execute(any(), any())
            } returns mockFiatList
            viewModel.onShowListBClick()
            assertEquals(awaitItem(), mockFiatList)

            // full list
            coEvery {
                filterCurrencyListUseCase.execute(any(), any())
            } returns mockList
            viewModel.onShowAllClick()
            assertEquals(awaitItem(), mockList)
        }
    }

    @Test
    fun `search from full list success`() = runTest {
        viewModel.displayList.test {
            consumeInitialEmit()

            val mockSearchTerm = "BT"
            every {
                searchCurrencyUseCase.execute(any(), any())
            } returns listOf(
                CurrencyModel.Coin(
                    shortName = "BTC",
                    name = "Bitcoin",
                    "BTC",
                )
            )

            viewModel.searchTerm(mockSearchTerm)

            assertEquals(awaitItem(), listOf(
                CurrencyModel.Coin(
                    shortName = "BTC",
                    name = "Bitcoin",
                    "BTC",
                )
            ))
        }
    }

    @Test
    fun `search from full list success then change to fiat list return no result`() = runTest {
        viewModel.displayList.test {
            consumeInitialEmit()

            val mockSearchTerm = "BT"
            every {
                searchCurrencyUseCase.execute(any(), any())
            } returns listOf(
                CurrencyModel.Coin(
                    shortName = "BTC",
                    name = "Bitcoin",
                    "BTC",
                )
            )

            viewModel.searchTerm(mockSearchTerm)

            assertEquals(awaitItem(), listOf(
                CurrencyModel.Coin(
                    shortName = "BTC",
                    name = "Bitcoin",
                    "BTC",
                )
            ))

            every {
                searchCurrencyUseCase.execute(any(), any())
            } returns emptyList()
            viewModel.onShowListBClick()
            assertEquals(awaitItem(), emptyList<CurrencyModel>())
        }
    }
}