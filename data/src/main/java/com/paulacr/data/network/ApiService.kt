package com.paulacr.data.network

import com.paulacr.domain.BitcoinPricing
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/charts/transactions-per-second")
    fun getBitcoinPricing(
        @Query("timespan") timeInterval: String,
        @Query("rollingAverage") rollingAverage: String,
        @Query("format") format: String = "json"
    ): Single<BitcoinPricing>

}