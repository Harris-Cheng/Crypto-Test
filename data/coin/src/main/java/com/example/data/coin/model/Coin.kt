package com.example.data.coin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Coin(
    @PrimaryKey
    @ColumnInfo(name = "uid")
    @SerialName("uid")
    val id: Int = 0,

    @ColumnInfo(name = "id")
    @SerialName("id")
    val shortName: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "symbol")
    val symbol: String,

    @ColumnInfo(name = "code")
    val code: String = ""
)
