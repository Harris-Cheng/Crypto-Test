package com.example.domain.di

import com.example.domain.usecase.DeleteCurrencyListUseCaseImpl
import com.example.domain.usecase.GetCurrencyListUseCaseImpl
import com.example.domain.usecase.IDeleteCurrencyListUseCase
import com.example.domain.usecase.IGetCurrencyListUseCase
import com.example.domain.usecase.IInsertCurrencyListUseCase
import com.example.domain.usecase.InsertCurrencyListUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val currencyUseCaseModule = module {
    factoryOf(::GetCurrencyListUseCaseImpl) bind IGetCurrencyListUseCase::class
    factoryOf(::InsertCurrencyListUseCaseImpl) bind IInsertCurrencyListUseCase::class
    factoryOf(::DeleteCurrencyListUseCaseImpl) bind IDeleteCurrencyListUseCase::class
}