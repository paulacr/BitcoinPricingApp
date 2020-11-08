package com.paulacr.bitcoinpricingapp

import com.paulacr.data.common.getFormattedDateTime
import com.paulacr.data.mapper.BitcoinPricingMapper
import com.paulacr.data.network.ApiService
import com.paulacr.data.repository.BitcoinPriceListCache
import com.paulacr.data.repository.BitcoinPriceRepository
import com.paulacr.data.repository.BitcoinPriceRepositoryImpl
import com.paulacr.domain.BitcoinPriceRawData
import com.paulacr.domain.Price
import com.paulacr.domain.PriceRawValue
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as mockitoWhen

@RunWith(MockitoJUnitRunner::class)
class BitcoinPriceRepositoryTest {

    @Rule
    @JvmField
    var testSchedule = RxRule()

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var cache: BitcoinPriceListCache

    private var mapper = BitcoinPricingMapper

    private lateinit var repository: BitcoinPriceRepository

    @Before
    fun onStart() {
        MockitoAnnotations.initMocks(this)
        repository = BitcoinPriceRepositoryImpl(apiService, cache, mapper)
    }

    @Test
    fun shouldSaveDataWhenGettingPriceFromRemote() {

        val price = PriceRawValue(1601741700, 3.540104166666666)
        val apiPricing = BitcoinPriceRawData(
            "ok", "Transaction Rate", "Transactions Per Second", "minute",
            "The number of Bitcoin transactions added to the mempool per second.",
            listOf(price)
        )

        val dateTime = price.timeStamp.getFormattedDateTime()
        val cacheData = listOf(
            Price(dateTime.first, dateTime.second, 1601741700, 3.540104166666666)
        )

        mockitoWhen(apiService.getBitcoinPricing()).thenReturn(Single.just(apiPricing))

        val result = repository.getRemoteBitcoinPrice().take(1)

        result
            .test()
            .assertValue(cacheData)

        verify(cache).saveData(cacheData)
        assertEquals(mapper.map(apiPricing).name, "Transaction Rate")
        assertEquals(mapper.map(apiPricing).period, "minute")
        assertEquals(
            mapper.map(apiPricing).description,
            "The number of Bitcoin transactions added to the mempool per second."
        )
        assertEquals(
            mapper.map(apiPricing).prices,
            listOf(
                Price(
                    cacheData[0].date,
                    cacheData[0].time,
                    cacheData[0].dateTimeInMillis,
                    cacheData[0].price
                )
            )
        )
    }
}
