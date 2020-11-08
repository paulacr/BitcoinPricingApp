package com.paulacr.bitcoinpricingapp

import com.paulacr.data.mapper.BitcoinPricingMapper
import com.paulacr.data.network.ApiService
import com.paulacr.data.repository.BitcoinPriceListCache
import com.paulacr.data.repository.BitcoinPriceRepository
import com.paulacr.data.repository.BitcoinPriceRepositoryImpl
import com.paulacr.domain.BitcoinPriceRawData
import com.paulacr.domain.Price
import com.paulacr.domain.PriceRawValue
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import java.net.UnknownHostException
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when` as mockitoWhen
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BitcoinPricingRepositoryTest {

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
    fun shouldEmitPriceListWhenRemoteBitcoinPriceisCalled() {

        val apiPricing = BitcoinPriceRawData(
            "ok", "Transaction Rate", "Transactions Per Second", "minute",
            "The number of Bitcoin transactions added to the mempool per second.", listOf(
                PriceRawValue(1601741700, 3.540104166666666)
            )
        )

        mockitoWhen(apiService.getBitcoinPricing()).thenReturn(Single.just(apiPricing))

        val result = repository.getRemoteBitcoinPrice()
        val testObserver: TestObserver<List<Price>> = TestObserver()
        result.subscribe(testObserver)

        val expectedValue = listOf(
            Price("2020-10-03", "13:15:00", 1601741700, 3.540104166666666)
        )

        testObserver
            .assertValue(expectedValue)
            .assertComplete()

        verify(apiService).getBitcoinPricing()
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
                    expectedValue[0].date,
                    expectedValue[0].time,
                    expectedValue[0].dateTimeInMillis,
                    expectedValue[0].price
                )
            )
        )
    }

    @Test
    fun shouldSavePricesListInCacheWhenRemoteBitcoinPriceIsSuccess() {

        val apiPricing = BitcoinPriceRawData(
            "ok", "Transaction Rate", "Transactions Per Second", "minute",
            "The number of Bitcoin transactions added to the mempool per second.", listOf(
                PriceRawValue(1601741700, 3.540104166666666)
            )
        )

        mockitoWhen(apiService.getBitcoinPricing()).thenReturn(Single.just(apiPricing))

        val result = repository.getRemoteBitcoinPrice()
        val testObserver: TestObserver<List<Price>> = TestObserver()
        result.subscribe(testObserver)

        val expectedValue = listOf(
            Price("2020-10-03", "13:15:00", 1601741700, 3.540104166666666)
        )

        testObserver
            .assertValue(expectedValue)
            .assertComplete()

        verify(apiService).getBitcoinPricing()
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
                    expectedValue[0].date,
                    expectedValue[0].time,
                    expectedValue[0].dateTimeInMillis,
                    expectedValue[0].price
                )
            )
        )

        assertEquals(cache.getData(), expectedValue)
        assertEquals(cache.getData()?.size, 1)
    }

    @Test
    fun shouldClearCacheBeforeAddingNewData() {

        val apiPricing = BitcoinPriceRawData(
            "ok", "Transaction Rate", "Transactions Per Second", "minute",
            "The number of Bitcoin transactions added to the mempool per second.", listOf(
                PriceRawValue(1601741700, 3.540104166666666)
            )
        )

        mockitoWhen(apiService.getBitcoinPricing()).thenReturn(Single.just(apiPricing))

        val expectedData = listOf(
            Price("2020-10-03", "13:15:00", 1601741700, 3.540104166666666)
        )

        val result = repository.getRemoteBitcoinPrice()
        val testObserver: TestObserver<List<Price>> = TestObserver()
        result.subscribe(testObserver)

        verify(cache).saveData(expectedData)
    }

    @Test(expected = UnknownHostException::class)
    fun shouldReturnErrorWhenHasNoInternetConnection() {

        mockitoWhen(apiService.getBitcoinPricing())
            .thenThrow(UnknownHostException::class.java)

        repository.getRemoteBitcoinPrice()
    }
}
