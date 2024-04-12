package com.example.data.coin

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.turbineScope
import com.example.data.coin.room.CoinDao
import com.example.data.coin.room.CoinDataBase
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CoinDataBaseTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var coinDataBase: CoinDataBase
    private lateinit var coinDao: CoinDao

    @Before
    fun setup() {
        coinDataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CoinDataBase::class.java,
        ).allowMainThreadQueries().build()

        coinDao = coinDataBase.coinDao()
    }

    @After
    fun tearDown() {
        coinDataBase.close()
    }

    @Test
    fun get_currency_list_returns_full_list_success() = runTest {
        turbineScope {
            coinDao.insertAll(DataBaseTestUtil.createCurrencyList(10))
            val coinFlow = coinDao.getAll().testIn(backgroundScope)
            assertEquals(coinFlow.awaitItem().size, 10)
        }
    }

    @Test
    fun get_currency_list_returns_empty_after_delete_all() = runTest {
        turbineScope {
            coinDao.insertAll(DataBaseTestUtil.createCurrencyList(10))
            val coinFlow = coinDao.getAll().testIn(backgroundScope)
            assertEquals(coinFlow.awaitItem().size, 10)
            coinDao.deleteAll()
            assertEquals(coinFlow.awaitItem().size, 0)
        }
    }

    @Test
    fun insert_same_list_with_replace_success() = runTest {
        turbineScope {
            coinDao.insertAll(DataBaseTestUtil.createCurrencyList(5))
            val coinFlow = coinDao.getAll().testIn(backgroundScope)
            assertEquals(coinFlow.awaitItem().size, 5)
            coinDao.insertAll(DataBaseTestUtil.createCurrencyList(5))
            assertEquals(coinFlow.awaitItem().size, 5)
        }
    }

}