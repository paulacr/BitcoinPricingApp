package com.paulacr.data.repository

import com.paulacr.data.RxSchedulerTestRule
import com.paulacr.data.mapper.BitcoinPricingMapper
import com.paulacr.data.network.ApiService
import com.paulacr.domain.BitcoinPrice
import com.paulacr.domain.BitcoinPriceRawData
import com.paulacr.domain.Price
import com.paulacr.domain.PriceRawValue
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BitcoinPriceRepositoryTest {

    @RelaxedMockK
    lateinit var apiService: ApiService

    @RelaxedMockK
    lateinit var cache: BitcoinPriceListCache

    @RelaxedMockK
    lateinit var mapper: BitcoinPricingMapper

    @Rule @JvmField
    var rule = RxSchedulerTestRule()

    lateinit var repository: BitcoinPriceRepository

    private val priceRawValue = mockk<PriceRawValue>(relaxed = true) {
        every { timeStamp } returns 1601741700
        every { price } returns 3.540104166666666
    }

    private val apiPricing = mockk<BitcoinPriceRawData>(relaxed = true) {
        every { status } returns "ok"
        every { name } returns "Transaction Rate"
        every { unit } returns "Transactions Per Second"
        every { period } returns "minute"
        every { description } returns "The number of Bitcoin transactions added to the mempool per second."
        every { prices } returns listOf(priceRawValue)
    }

    private val price = mockk<Price>(relaxed = true) {
        every { date } returns "2020-03-03"
        every { time } returns "20:15"
        every { dateTimeInMillis } returns 1601741700
        every { price } returns 3.123456
    }

    private val bitcoinPrice = mockk<BitcoinPrice>(relaxed = true) {
        every { name } returns "Transaction Rate"
        every { period } returns "minute"
        every { description } returns "The number of Bitcoin transactions added to the mempool per second."
        every { prices } returns listOf(price)
    }

    @Before
    fun onStart() {
        MockKAnnotations.init(this)
        repository = BitcoinPriceRepositoryImpl(apiService, cache, mapper)
    }

    @Test
    fun shouldSaveDataWhenGettingPriceFromRemote() {
        every { apiService.getBitcoinPricing() } returns Single.just(apiPricing)
        every { cache.getData() } returns listOf(price)
        every { mapper.map(apiPricing) } returns bitcoinPrice

        repository.fetchBitcoinPrice()
        verify {
            cache.getData()
//            mapper.map(apiPricing)
        }
    }
}
