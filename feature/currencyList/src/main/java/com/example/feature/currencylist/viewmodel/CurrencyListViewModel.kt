package com.example.feature.currencylist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.IGetCurrencyListUseCase
import com.example.domain.usecase.IInsertCurrencyListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CurrencyListViewModel(
    getCurrencyListUseCase: IGetCurrencyListUseCase,
    insertCurrencyListUseCase: IInsertCurrencyListUseCase,
): ViewModel() {
    val originalListFlow = getCurrencyListUseCase.execute().flowOn(Dispatchers.IO).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            insertCurrencyListUseCase.execute()
        }
    }
}