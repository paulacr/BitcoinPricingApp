package com.paulacr.data.network

import com.paulacr.domain.BitcoinPriceRawData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/charts/market-price")
    fun getBitcoinPricing(
        @Query("timespan") timeInterval: String? = null,
        @Query("rollingAverage") rollingAverage: String? = null,
        @Query("format") format: String? = null
    ): Single<BitcoinPriceRawData>
}