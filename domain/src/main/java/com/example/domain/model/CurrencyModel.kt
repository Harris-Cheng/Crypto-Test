package com.example.domain.model

import com.example.data.coin.model.Coin

data class CurrencyModel(
    val shortName: String,
    val name: String,
    val symbol: String,
    val isFiat: Boolean
) {
    companion object {
        fun Coin.mapToCurrencyModel() = CurrencyModel(
            shortName = shortName,
            name = name,
            symbol = symbol,
            isFiat = code.isNotEmpty()
        )
    }
}