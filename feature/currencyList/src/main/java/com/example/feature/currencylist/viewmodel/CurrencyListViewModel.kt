package com.example.feature.currencylist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.AppCoroutineDispatcher
import com.example.common.utils.throttleLatest
import com.example.domain.usecase.IDeleteCurrencyListUseCase
import com.example.domain.usecase.IFilterCurrencyTypeUseCase
import com.example.domain.usecase.IGetCurrencyListUseCase
import com.example.domain.usecase.IInsertCurrencyListUseCase
import com.example.domain.usecase.ISearchCurrencyUseCase
import com.example.domain.usecase.ListType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CurrencyListViewModel(
    private val appCoroutineDispatcher: AppCoroutineDispatcher,
    getCurrencyListUseCase: IGetCurrencyListUseCase,
    private val insertCurrencyListUseCase: IInsertCurrencyListUseCase,
    private val deleteCurrencyUseCase: IDeleteCurrencyListUseCase,
    private val filterCurrencyListUseCase: IFilterCurrencyTypeUseCase,
    private val searchCurrencyListUseCase: ISearchCurrencyUseCase
): ViewModel() {

    private val listTypeFlow = MutableStateFlow<ListType>(ListType.All)

    private val originalListFlow = getCurrencyListUseCase.execute().flowOn(appCoroutineDispatcher.io).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val searchTermFlow = MutableStateFlow("")

    val displayList = originalListFlow.combine(
        listTypeFlow
    ) { list, type ->
        filterCurrencyListUseCase.execute(list, type)
    }.combine(searchTermFlow) { filteredList, keyword ->
        if (keyword.isBlank()) {
            filteredList
        } else {
            searchCurrencyListUseCase.execute(
                filteredList, keyword
            )
        }
    }.flowOn(appCoroutineDispatcher.io).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val isEmptyList = displayList.map {
        it.isEmpty()
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

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

    fun searchTerm(keyword: String) {
        updateSearchKeyword(keyword)
    }

    private fun deleteAllList() {
        viewModelScope.launch(appCoroutineDispatcher.io) {
            deleteCurrencyUseCase.execute()
        }
    }

    private fun insertAllList() {
        viewModelScope.launch(appCoroutineDispatcher.io) {
            insertCurrencyListUseCase.execute()
        }
    }

    private fun filterListByType(type: ListType) {
        listTypeFlow.update {
            type
        }
    }

    private fun updateSearchKeyword(keyword: String) {
        searchTermFlow.value = keyword
    }
}