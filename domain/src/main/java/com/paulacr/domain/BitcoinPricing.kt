package com.paulacr.domain

import com.google.gson.annotations.SerializedName

data class BitcoinPricing(
    val status: String,
    val name: String,
    val unit: String,
    val period: String,
    val description: String,
    @SerializedName("values")
    val values: List<ChartCoordinates>)

data class ChartCoordinates(
    @SerializedName("x")
    val xCoordinateValue: Double,
    @SerializedName("y")
    val yCoordinateValue: Double
)