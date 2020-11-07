package com.paulacr.domain

import com.google.gson.annotations.SerializedName

data class BitcoinPricing(
    val status: String,
    val name: String,
    val unit: String,
    val period: String,
    val description: String,
    @SerializedName("values")
    val coordinatesValues: List<TransactionCoordinates>)

data class TransactionCoordinates(
    @SerializedName("x")
    val xCoordinateValue: Double,
    @SerializedName("y")
    val yCoordinateValue: Double
)