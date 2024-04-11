package com.example.domain.model

import com.example.data.coin.model.Coin

data class CurrencyModel(
    val id: Int,
    val name: String,
    val symbol: String,
    val isFiat: Boolean
) {
    companion object {
        fun Coin.mapToCurrencyModel() = CurrencyModel(
            id = id,
            name = name,
            symbol = symbol,
            isFiat = code.isNotEmpty()
        )
    }
}