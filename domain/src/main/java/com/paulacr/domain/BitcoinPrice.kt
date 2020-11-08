package com.paulacr.domain

data class BitcoinPrice(val name: String,
                        val period: String,
                        val description: String,
                        val prices: List<Price>)

data class Price(
    val date: String,
    val time: String,
    val dateTimeInMillis: Long,
    val price: Double
)