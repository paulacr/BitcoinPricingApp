package com.paulacr.bitcoinpricingapp

import com.paulacr.data.mapper.BitcoinPricingMapper
import com.paulacr.data.network.ApiService
import com.paulacr.domain.BitcoinPrice
import com.paulacr.domain.BitcoinPriceRawData
import com.paulacr.domain.Price
import com.paulacr.domain.PriceRawValue
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.net.UnknownHostException
import org.mockito.Mockito.`when` as mockitoWhen

@RunWith(MockitoJUnitRunner::class)
class BitcoinPricingRepositoryTest {

    @Rule
    @JvmField
    var testSchedule = RxRule()

    @Mock
    lateinit var apiService: ApiService

    private lateinit var repository: RemoteBitcoinPricingRepository

    private var mapper = BitcoinPricingMapper

    @Before
    fun onStart() {
        MockitoAnnotations.initMocks(this)
        repository = RemoteBitcoinPricingRepositoryImpl(apiService, mapper)
    }

    @Test
    fun shouldGetLast5WeeksBitcoinPricingAndMapToPricing() {

        val apiPricing = BitcoinPriceRawData(
            "ok", "Transaction Rate", "Transactions Per Second", "minute",
            "The number of Bitcoin transactions added to the mempool per second.", listOf(
                PriceRawValue(1601741700, 3.540104166666666)
            )
        )

        mockitoWhen(apiService.getBitcoinPricing()).thenReturn(Single.just(apiPricing))

        val result = repository.getBitcoinPricing()
        val testObserver: TestObserver<BitcoinPrice> = TestObserver()
        result.subscribe(testObserver)

        val expectedValue = BitcoinPrice(
            "Transaction Rate", "minute",
            "The number of Bitcoin transactions added to the mempool per second.", listOf(
                Price("2020-10-03", "16:15:00", 3.540104166666666)
            )
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
                    expectedValue.prices[0].date,
                    expectedValue.prices[0].time,
                    3.540104166666666
                )
            )
        )
    }


    @Test(expected=UnknownHostException::class)
    fun shouldReturnErrorWhenHasNoInternetConnection() {

        mockitoWhen(apiService.getBitcoinPricing())
            .thenThrow(UnknownHostException::class.java)

        repository.getBitcoinPrice()
    }
}
