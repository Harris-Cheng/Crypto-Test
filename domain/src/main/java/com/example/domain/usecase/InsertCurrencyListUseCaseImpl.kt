package com.example.domain.usecase

import com.example.data.coin.repository.ICurrencyListRepository

internal class InsertCurrencyListUseCaseImpl(
    private val repository: ICurrencyListRepository
): IInsertCurrencyListUseCase {
    override suspend fun execute() {
        repository.insertAll(TODO())
    }
}