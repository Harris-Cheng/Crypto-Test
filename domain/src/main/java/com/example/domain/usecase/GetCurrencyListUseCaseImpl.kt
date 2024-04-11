package com.example.domain.usecase

import com.example.data.coin.repository.ICurrencyListRepository
import com.example.domain.model.CurrencyModel
import com.example.domain.model.CurrencyModel.Companion.mapToCurrencyModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetCurrencyListUseCaseImpl(
    private val repository: ICurrencyListRepository
): IGetCurrencyListUseCase {
    override fun execute(): Flow<List<CurrencyModel>> {
        return repository.getCurrencyListFlow().map { list ->
            list.map { coin ->
                coin.mapToCurrencyModel()
            }
        }
    }
}