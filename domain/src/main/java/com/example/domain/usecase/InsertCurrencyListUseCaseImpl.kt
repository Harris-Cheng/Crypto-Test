package com.example.domain.usecase

import android.content.Context
import com.example.data.coin.model.Coin
import com.example.data.coin.repository.ICurrencyListRepository
import com.example.domain.R
import kotlinx.serialization.json.Json

internal class InsertCurrencyListUseCaseImpl(
    private val context: Context,
    private val repository: ICurrencyListRepository
): IInsertCurrencyListUseCase {

    override suspend fun execute(): Result<Unit> = runCatching {
        (context.resources.openRawResource(R.raw.currency_list_a).bufferedReader().use {
            it.readText()
        }.let { currencyAJson ->
            Json.decodeFromString<List<Coin>>(currencyAJson)
        } + context.resources.openRawResource(R.raw.currency_list_b).bufferedReader().use {
            it.readText()
        }.let { currencyBJson ->
            Json.decodeFromString<List<Coin>>(currencyBJson)
        }).also {
            repository.insertAll(it.mapIndexed { index, coin ->
                coin.copy(
                    id = index
                )
            })
        }
    }
}