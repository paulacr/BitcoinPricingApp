package com.paulacr.data.mapper

import com.paulacr.data.common.DateConverter.getFormattedDateTime
import com.paulacr.domain.BitcoinPriceRawData
import com.paulacr.domain.BitcoinPrice
import com.paulacr.domain.Price
import com.paulacr.domain.PriceRawValue

object BitcoinPricingMapper {

    fun map(rawData: BitcoinPriceRawData) = BitcoinPrice(
        name = rawData.name,
        period = rawData.period,
        description = rawData.description,
        prices = getPrices(rawData.prices)
    )

    private fun getPrices(pricesList: List<PriceRawValue>): MutableList<Price> {
        val bitcoinPrices = mutableListOf<Price>()
        var dateTime: Pair<String, String>
        pricesList.forEach {
            dateTime = it.timeStamp.getFormattedDateTime()
            bitcoinPrices.add(
                Price(
                    date = dateTime.first,
                    time = dateTime.second,
                    dateTimeInMillis = it.timeStamp,
                    price = it.price
                )
            )
        }

        return bitcoinPrices
    }
}