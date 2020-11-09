package com.paulacr.domain

import com.google.gson.annotations.SerializedName

data class BitcoinPriceRawData(
    val status: String,
    val name: String,
    val unit: String,
    val period: String,
    val description: String,
    @SerializedName("values")
    val prices: List<PriceRawValue>)

data class PriceRawValue(
    @SerializedName("x")
    val timeStamp: Long,
    @SerializedName("y")
    val price: Double
)