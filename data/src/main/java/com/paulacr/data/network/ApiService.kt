package com.paulacr.data.network

import com.paulacr.domain.BitcoinPricing
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/charts/transactions-per-second")
    fun getBitcoinPricing(
        @Query("timespan") timeInterval: String? = null,
        @Query("rollingAverage") rollingAverage: String? = null,
        @Query("format") format: String? = null
    ): Single<BitcoinPricing>
}