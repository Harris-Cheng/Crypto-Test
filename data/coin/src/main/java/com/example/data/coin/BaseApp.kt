package com.example.data.coin

import android.app.Application
import com.example.data.coin.di.coinDataBaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

abstract class BaseApp: Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@BaseApp)
            modules(
                coinDataBaseModule
            )
        }
    }
}