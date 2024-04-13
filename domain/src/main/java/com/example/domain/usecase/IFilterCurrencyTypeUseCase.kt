package com.example.domain.usecase

import com.example.domain.model.CurrencyModel

sealed interface ListType {
    data object Coin: ListType

    data object Fiat: ListType

    data object All: ListType
}

interface IFilterCurrencyTypeUseCase {
    suspend fun execute(list: List<CurrencyModel>, type: ListType): List<CurrencyModel>
}