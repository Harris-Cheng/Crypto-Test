package com.example.domain.usecase

import com.example.domain.model.CurrencyModel
import kotlinx.coroutines.flow.Flow

interface IGetCurrencyListUseCase {
    fun execute(): Flow<List<CurrencyModel>>
}