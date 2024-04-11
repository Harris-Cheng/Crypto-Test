package com.example.data.coin.repository

import com.example.data.coin.model.Coin
import kotlinx.coroutines.flow.Flow

interface ICurrencyListRepository {
    fun getCurrencyListFlow(): Flow<List<Coin>>

    suspend fun deleteAll()

    suspend fun insertAll(list: List<Coin>)
}