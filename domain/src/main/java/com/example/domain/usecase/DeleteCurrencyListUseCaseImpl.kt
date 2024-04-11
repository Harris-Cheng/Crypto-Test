package com.example.domain.usecase

import com.example.data.coin.repository.ICurrencyListRepository

internal class DeleteCurrencyListUseCaseImpl(
    private val repository: ICurrencyListRepository
): IDeleteCurrencyListUseCase {
    override suspend fun execute() {
        repository.deleteAll()
    }
}