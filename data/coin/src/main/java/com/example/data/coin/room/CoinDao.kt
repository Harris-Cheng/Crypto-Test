package com.example.data.coin.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.coin.model.Coin
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Query("SELECT * FROM coin")
    fun getAll(): Flow<List<Coin>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(peoples: List<Coin>)

    @Query("DELETE FROM coin")
    suspend fun deleteAll()
}