package com.example.domain.usecase

import com.example.domain.model.CurrencyModel

internal class FilterCurrencyTypeUseCaseImpl: IFilterCurrencyTypeUseCase {
    override suspend fun execute(
        list: List<CurrencyModel>,
        type: ListType
    ): List<CurrencyModel> {
        return list.filterCurrencyType(type)
    }

    private fun List<CurrencyModel>.filterCurrencyType(type: ListType): List<CurrencyModel> {
        return when (type) {
            ListType.All -> this
            ListType.ListA -> filterIsInstance<CurrencyModel.Coin>()
            ListType.ListB -> filterIsInstance<CurrencyModel.Fiat>()
        }
    }
}