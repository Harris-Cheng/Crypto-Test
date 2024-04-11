package com.example.data.coin.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.coin.model.Coin

@Database(entities = [Coin::class], version = 1)
abstract class CoinDataBase: RoomDatabase() {
    abstract fun coinDao(): CoinDao
}