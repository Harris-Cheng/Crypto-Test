package com.example.data.coin.repository

import com.example.data.coin.datasource.CurrencyListDataSource
import com.example.data.coin.model.Coin
import kotlinx.coroutines.flow.Flow

internal class CurrencyListRepositoryImpl(
    private val dataSource: CurrencyListDataSource
): ICurrencyListRepository {
    override fun getCurrencyListFlow(): Flow<List<Coin>> {
        return dataSource.getCurrencyList()
    }

    override suspend fun deleteAll() {
        dataSource.deleteAll()
    }

    override suspend fun insertAll(list: List<Coin>) {
        dataSource.insertAll(list)
    }
}