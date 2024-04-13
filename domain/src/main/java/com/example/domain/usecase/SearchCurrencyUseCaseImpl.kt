package com.example.domain.usecase

import com.example.domain.model.CurrencyModel

class SearchCurrencyUseCaseImpl: ISearchCurrencyUseCase {
    override fun execute(list: List<CurrencyModel>, keyword: String): List<CurrencyModel> {
        return list.filter { model ->
            model.name.lowercase().startsWith(keyword.lowercase()) or
                    model.name.lowercase().contains(" ${keyword.lowercase()}") or
                    model.symbol.lowercase().startsWith(keyword.lowercase())
        }
    }
}