package com.example.data.coin

import com.example.data.coin.model.Coin

object DataBaseTestUtil {
    fun createCurrencyList(num: Int) = List(num) {
        Coin(
            id = it,
            shortName = "C$it",
            name = "Coin$it",
            symbol = "C$it",
            code = ""
        )
    }
}