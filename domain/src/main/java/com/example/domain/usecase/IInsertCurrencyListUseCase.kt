package com.example.domain.usecase

interface IInsertCurrencyListUseCase {
    suspend fun execute(): Result<Unit>
}