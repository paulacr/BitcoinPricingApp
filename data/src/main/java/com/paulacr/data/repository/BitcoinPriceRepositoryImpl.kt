package com.paulacr.data.repository

import com.paulacr.data.common.Period
import com.paulacr.data.common.getLocalDateTimeTo
import com.paulacr.data.common.localDateTimeNow
import com.paulacr.data.mapper.BitcoinPricingMapper
import com.paulacr.data.network.ApiService
import com.paulacr.domain.Price
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val REFRESH_UPDATE = 20L

class BitcoinPriceRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val cache: BitcoinPriceListCache,
    private val mapper: BitcoinPricingMapper
) : BitcoinPriceRepository {

    private var lastRefreshTime: LocalDateTime = localDateTimeNow()

    override fun fetchBitcoinPrice(): Flowable<List<Price>> {
        return getRemoteBitcoinPrice().startWith(cache.getData())
    }

    override fun getLocalBitcoinPrice(): List<Price> {
        return cache.getData()
    }

    override fun getRemoteBitcoinPrice(
        timeInterval: String?,
        rollingAverage: String?
    ): Flowable<List<Price>> =

        apiService.getBitcoinPricing()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retryWhen {
                it.delay(REFRESH_UPDATE, TimeUnit.SECONDS)
            }.flatMap {
                val pricesMapper = mapper.map(it)
                saveBitcoinPriceInCache(pricesMapper.prices)
                Single.just(pricesMapper.prices)
            }.repeatWhen { completed ->
                completed.delay(REFRESH_UPDATE, TimeUnit.SECONDS)
            }

    override fun saveBitcoinPriceInCache(prices: List<Price>) {
        cache.saveData(prices)
    }

    override fun getBitcoinPriceForPeriod(period: Period): Flowable<List<Price>> {
        if (getLocalBitcoinPrice().isEmpty() || shouldRefreshData()) {
            return apiService.getBitcoinPricing(Period.THREE_YEARS.timeSpan)
                .subscribeOn(Schedulers.io())
                .doOnSuccess {
                    lastRefreshTime = localDateTimeNow()
                    val pricesMapper = mapper.map(it)
                    saveBitcoinPriceInCache(pricesMapper.prices)
                }.map(mapper::map)
                .map {
                    getLocalBitcoinPrice().filter {
                        filterPricesByPeriod(period, it)
                    }
                }.toFlowable()
        } else {
            return Observable.fromIterable(getLocalBitcoinPrice())
                .filter {
                    filterPricesByPeriod(period, it)
                }.toList().toFlowable()
        }
    }

    private fun shouldRefreshData() = lastRefreshTime < localDateTimeNow().minusDays(1)

    private fun filterPricesByPeriod(
        period: Period,
        it: Price
    ): Boolean {
        return if (period == Period.ONE_YEAR) {
            getLocalDateTimeTo(it.dateTimeInMillis) >= localDateTimeNow().minusYears(1)
        } else {
            getLocalDateTimeTo(it.dateTimeInMillis) >= localDateTimeNow().minusYears(3)
        }
    }
}