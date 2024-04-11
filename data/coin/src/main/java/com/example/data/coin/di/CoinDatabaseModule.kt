package com.example.data.coin.di

import androidx.room.Room
import com.example.data.coin.room.CoinDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val coinDataBaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            CoinDataBase::class.java, "coin-database"
        ).build()
    }
}