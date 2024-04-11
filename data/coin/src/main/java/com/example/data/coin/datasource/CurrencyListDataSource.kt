package com.example.data.coin.datasource

import com.example.data.coin.model.Coin
import com.example.data.coin.room.CoinDataBase
import kotlinx.coroutines.flow.Flow

internal class CurrencyListDataSource(
    private val coinDataBase: CoinDataBase
) {
    fun getCurrencyList(): Flow<List<Coin>> {
        return coinDataBase.coinDao().getAll()
    }

    suspend fun deleteAll() {
        coinDataBase.coinDao().deleteAll()
    }

    suspend fun insertAll(
        list: List<Coin>
    ) {
        coinDataBase.coinDao().insertAll(list)
    }
}