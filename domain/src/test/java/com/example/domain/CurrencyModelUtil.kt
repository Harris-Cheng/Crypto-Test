package com.example.domain

import com.example.domain.model.CurrencyModel

object CurrencyModelUtil {
    fun createCoinWithName(name: String) = CurrencyModel.Coin(
        shortName = "Short Name",
        name = name,
        symbol = "Symbol"
    )

    fun createCoinWithSymbol(symbol: String) = CurrencyModel.Coin(
        shortName = "Short Name",
        name = "Name",
        symbol = symbol
    )
}