package com.paulacr.bitcoinpricingapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.paulacr.data.usecase.BitcoinPricingUseCase
import com.paulacr.domain.Price
import com.paulacr.graph.GraphBuilderInterface
import io.reactivex.Flowable
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
class GraphViewModelTest {

    @Rule
    @JvmField
    var testSchedule = RxRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var useCase: BitcoinPricingUseCase

    @Mock
    lateinit var graphBuilder: GraphBuilderInterface

    private lateinit var viewModel: GraphViewModel

    @Before
    fun onStart() {
        MockitoAnnotations.initMocks(this)
        viewModel = GraphViewModel(useCase)
    }

    @Test
    fun shouldTryToGetBitcoinPriceWithSuccess() {
        val prices = listOf(
            Price("2020-10-03", "13:15:00", 1601741700, 3.540104166666666)
        )

        mockitoWhen(useCase.fetchBitcoinPrice()).thenReturn(Flowable.just(prices))

        viewModel.fetchBitcoinPricing()

        verify(useCase).fetchBitcoinPrice()
    }
}
