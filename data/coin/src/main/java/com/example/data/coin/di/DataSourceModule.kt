package com.example.data.coin.di

import com.example.data.coin.datasource.CurrencyListDataSource
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataSourceModule = module {
    factoryOf(::CurrencyListDataSource)
}