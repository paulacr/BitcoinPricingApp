package com.paulacr.data.repository

import com.paulacr.domain.BitcoinPrice
import com.paulacr.domain.Price
import okhttp3.internal.toImmutableList

class CacheBitcoinPrice {

    private val cacheData: MutableList<Price> = mutableListOf()

    fun saveData(prices: List<Price>) {
        cacheData.clear()
        cacheData.addAll(prices)
    }

    fun getData(): List<Price>? {
        return cacheData.toImmutableList()
    }
}