package com.paulacr.domain

data class Pricing(val name: String,
                   val period: String,
                   val description: String,
                   val coordinatesValues: List<TransactionCoordinates>)