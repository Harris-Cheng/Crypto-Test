package com.example.domain.usecase

import com.example.domain.model.CurrencyModel

interface ISearchCurrencyUseCase {
    fun execute(list: List<CurrencyModel>, keyword: String): List<CurrencyModel>
}