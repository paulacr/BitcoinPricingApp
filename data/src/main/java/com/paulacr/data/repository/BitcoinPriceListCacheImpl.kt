package com.paulacr.data.repository

import com.paulacr.domain.Price
import okhttp3.internal.toImmutableList
import javax.inject.Inject

interface BitcoinPriceListCache {

    fun saveData(data: List<Price>)

    fun getData(): List<Price>

}
class BitcoinPriceListCacheImpl @Inject constructor() : BitcoinPriceListCache {

    private val cacheData: MutableList<Price> = mutableListOf()

    override fun saveData(data: List<Price>) {
        cacheData.clear()
        cacheData.addAll(data)
    }

    override fun getData(): List<Price> {
        return cacheData.toImmutableList()
    }
}