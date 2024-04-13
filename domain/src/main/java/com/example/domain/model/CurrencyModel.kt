package com.example.domain.model

sealed class CurrencyModel {
    abstract val shortName: String
    abstract val name: String
    abstract val symbol: String

    data class Coin(
        override val shortName: String,
        override val name: String,
        override val symbol: String
    ): CurrencyModel()

    data class Fiat(
        override val shortName: String,
        override val name: String,
        override val symbol: String
    ): CurrencyModel()
}