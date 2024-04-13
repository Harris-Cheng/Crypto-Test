package com.example.domain.di

import com.example.domain.usecase.DeleteCurrencyListUseCaseImpl
import com.example.domain.usecase.FilterCurrencyTypeUseCaseImpl
import com.example.domain.usecase.GetCurrencyListUseCaseImpl
import com.example.domain.usecase.IDeleteCurrencyListUseCase
import com.example.domain.usecase.IFilterCurrencyTypeUseCase
import com.example.domain.usecase.IGetCurrencyListUseCase
import com.example.domain.usecase.IInsertCurrencyListUseCase
import com.example.domain.usecase.ISearchCurrencyUseCase
import com.example.domain.usecase.InsertCurrencyListUseCaseImpl
import com.example.domain.usecase.SearchCurrencyUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val currencyUseCaseModule = module {
    factoryOf(::GetCurrencyListUseCaseImpl) bind IGetCurrencyListUseCase::class
    factoryOf(::InsertCurrencyListUseCaseImpl) bind IInsertCurrencyListUseCase::class
    factoryOf(::DeleteCurrencyListUseCaseImpl) bind IDeleteCurrencyListUseCase::class
    factoryOf(::FilterCurrencyTypeUseCaseImpl) bind IFilterCurrencyTypeUseCase::class
    factoryOf(::SearchCurrencyUseCaseImpl) bind ISearchCurrencyUseCase::class
}