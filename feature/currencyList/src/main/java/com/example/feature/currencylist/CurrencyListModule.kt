package com.example.feature.currencylist

import com.example.feature.currencylist.viewmodel.CurrencyListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val currencyListModule = module {
    viewModelOf(::CurrencyListViewModel)
}