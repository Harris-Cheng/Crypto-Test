package com.example.data.coin.room

import androidx.room.Dao
import androidx.room.Query
import com.example.data.coin.model.Coin
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Query("SELECT * FROM coin")
    fun getAll(): Flow<List<Coin>>
}