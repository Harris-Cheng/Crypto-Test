package com.example.cryptotest

import android.app.Application
import com.example.data.coin.di.coinDataBaseModule
import com.example.data.coin.di.dataSourceModule
import com.example.data.coin.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DemoApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@DemoApplication)
            modules(
                coinDataBaseModule,
                dataSourceModule,
                repositoryModule
            )
        }
    }
}