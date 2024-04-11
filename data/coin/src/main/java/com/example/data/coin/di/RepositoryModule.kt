package com.example.data.coin.di

import com.example.data.coin.repository.CurrencyListRepositoryImpl
import com.example.data.coin.repository.ICurrencyListRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    factoryOf(::CurrencyListRepositoryImpl) bind ICurrencyListRepository::class
}