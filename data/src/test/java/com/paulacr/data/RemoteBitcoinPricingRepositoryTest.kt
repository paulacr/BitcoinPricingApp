package com.paulacr.data

import com.paulacr.data.mapper.BitcoinPricingMapper
import com.paulacr.data.network.ApiService
import com.paulacr.data.repository.RemoteBitcoinPricingRepository
import com.paulacr.data.repository.RemoteBitcoinPricingRepositoryImpl
import com.paulacr.domain.BitcoinPricing
import com.paulacr.domain.TransactionCoordinates
import com.paulacr.domain.Pricing
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
import java.io.IOException
import java.net.UnknownHostException
import org.mockito.Mockito.`when` as mockitoWhen

@RunWith(MockitoJUnitRunner::class)
class RemoteBitcoinPricingRepositoryTest {

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

        val apiPricing = BitcoinPricing(
            "online", "Testing", "unit", "8hours",
            "some description", listOf(
                TransactionCoordinates(123456.0, 12.34467)
            )
        )

        mockitoWhen(apiService.getBitcoinPricing()).thenReturn(Single.just(apiPricing))

        val result = repository.getBitcoinPricing()
        val testObserver: TestObserver<Pricing> = TestObserver()
        result.subscribe(testObserver)

        val expectedValue = Pricing(
            "Testing", "8hours",
            "some description", listOf(
                TransactionCoordinates(123456.0, 12.34467)
            )
        )

        testObserver
            .assertValue(expectedValue)
            .assertComplete()

        verify(apiService).getBitcoinPricing()
        assertEquals(mapper.map(apiPricing).name, "Testing")
        assertEquals(mapper.map(apiPricing).period, "8hours")
        assertEquals(mapper.map(apiPricing).description, "some description")
        assertEquals(
            mapper.map(apiPricing).coordinatesValues,
            listOf(TransactionCoordinates(123456.0, 12.34467))
        )
    }

    @Test
    fun shouldGetCurrentBitcoinPricingAndMapToPricing() {

        val apiPricing = BitcoinPricing(
            "online", "Testing", "unit", "8hours",
            "some description", listOf(
                TransactionCoordinates(123456.0, 12.34467)
            )
        )

        mockitoWhen(apiService.getBitcoinPricing("5weeks")).thenReturn(Single.just(apiPricing))

        val result = repository.getBitcoinPricing()
        val testObserver: TestObserver<Pricing> = TestObserver()
        result.subscribe(testObserver)

        val expectedValue = Pricing(
            "Testing", "8hours",
            "some description", listOf(
                TransactionCoordinates(123456.0, 12.34467)
            )
        )

        testObserver
            .assertValue(expectedValue)
            .assertComplete()

        verify(apiService).getBitcoinPricing()
        assertEquals(mapper.map(apiPricing).name, "Testing")
        assertEquals(mapper.map(apiPricing).period, "8hours")
        assertEquals(mapper.map(apiPricing).description, "some description")
        assertEquals(
            mapper.map(apiPricing).coordinatesValues,
            listOf(TransactionCoordinates(123456.0, 12.34467))
        )
    }

    @Test(expected=UnknownHostException::class)
    fun shouldReturnErrorWhenHasNoInternetConnection() {

        mockitoWhen(apiService.getBitcoinPricing())
            .thenReturn(Single.error(IOException()))

        val result = repository.getBitcoinPricing()
        val testObserver: TestObserver<Pricing> = TestObserver()
        result.subscribe(testObserver)

        testObserver
            .assertNoErrors()
            .assertComplete()
    }
}