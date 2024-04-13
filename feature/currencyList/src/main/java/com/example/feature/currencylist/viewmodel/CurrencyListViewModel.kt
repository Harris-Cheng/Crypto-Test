package com.example.feature.currencylist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.IDeleteCurrencyListUseCase
import com.example.domain.usecase.IFilterCurrencyTypeUseCase
import com.example.domain.usecase.IGetCurrencyListUseCase
import com.example.domain.usecase.IInsertCurrencyListUseCase
import com.example.domain.usecase.ListType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CurrencyListViewModel(
    getCurrencyListUseCase: IGetCurrencyListUseCase,
    private val insertCurrencyListUseCase: IInsertCurrencyListUseCase,
    private val deleteCurrencyUseCase: IDeleteCurrencyListUseCase,
    private val filterCurrencyListUseCase: IFilterCurrencyTypeUseCase,
): ViewModel() {

    private val listTypeFlow = MutableStateFlow<ListType>(ListType.All)

    private val originalListFlow = getCurrencyListUseCase.execute().flowOn(Dispatchers.IO).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val displayList = originalListFlow.combine(
        listTypeFlow
    ) { list, type ->
        filterCurrencyListUseCase.execute(list, type)
    }.flowOn(Dispatchers.IO).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun onClearAllClick() {
        deleteAllList()
    }

    fun onInsertAllClick() {
        insertAllList()
    }

    fun onShowListAClick() {
        filterListByType(ListType.Coin)
    }

    fun onShowListBClick() {
        filterListByType(ListType.Fiat)
    }

    fun onShowAllClick() {
        filterListByType(ListType.All)
    }

    private fun deleteAllList() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCurrencyUseCase.execute()
        }
    }

    private fun insertAllList() {
        viewModelScope.launch(Dispatchers.IO) {
            insertCurrencyListUseCase.execute()
        }
    }

    private fun filterListByType(type: ListType) {
        listTypeFlow.update {
            type
        }
    }
}