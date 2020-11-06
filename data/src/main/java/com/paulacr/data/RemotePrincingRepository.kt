package com.paulacr.data

import com.paulacr.data.network.ApiService
import com.paulacr.data.network.Network
import com.paulacr.domain.Chart
import io.reactivex.Single

class RemotePrincingRepository {

    private val apiService: ApiService = Network().getService()

    fun getChartData(): Single<Chart> = apiService.getChart(
        timeInterval = "5weeks",
        rollingAverage = "8hours"
    )
}