package com.paulacr.bitcoinpricingapp

import com.paulacr.data.usecase.BitcoinPricingUseCase
import com.paulacr.domain.Price
import com.paulacr.graph.GraphBuilder
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as mockitoWhen


@RunWith(MockitoJUnitRunner::class)
class GraphViewModelTest {

    @Rule
    @JvmField
    var testSchedule = RxRule()

    @Mock
    lateinit var useCase: BitcoinPricingUseCase

    @Mock
    lateinit var graphBuilder: GraphBuilder

    private lateinit var viewModel: GraphViewModel

    @Before
    fun onStart() {
        MockitoAnnotations.initMocks(this)
        viewModel = GraphViewModel(useCase, graphBuilder)
    }

    @Test
    fun shouldEmitPriceListWhenRemoteBitcoinPriceIsCalled() {
        val prices = listOf(
            Price("2020-10-03", "13:15:00", 1601741700, 3.540104166666666)
        )

        mockitoWhen(useCase.fetchBitcoinPrice()).thenReturn(Single.just(prices))

        val testScheduler = TestScheduler()
        Mockito.doReturn(testScheduler)
            .`when`(viewModel)
            .fetchBitcoinPricing()

        viewModel.fetchBitcoinPricing()

//        testObserver
//            .assertComplete()
//        val result = viewModel.fetchBitcoinPricing()
//        val testObserver: TestObserver<List<Price>> = TestObserver()
//        result.subscribe(testObserver)
//
//        testObserver
//            .assertValue(expectedValue)
//            .assertComplete()

        verify(useCase).getLocalBitcoinPrice()
    }
}
