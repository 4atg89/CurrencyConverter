package com.atg.data.market

import com.atg.domain.ConversionResultModel
import com.atg.domain.CurrencyModel
import com.atg.domain.MarketRepository
import com.atg.network.market.MarketService
import java.math.BigDecimal
import java.math.RoundingMode

class MarketRepositoryImpl(private val marketService: MarketService) : MarketRepository {

    override suspend fun currencies(): CurrencyModel =
        marketService.currencies().let { CurrencyModel(it.base, it.rates) }

    override suspend fun exchange(sellName: String, conversionAmount: Float, receiveName: String, fee: Float?): ConversionResultModel {
        val actual = marketService.currencies()
        val rate = actual.rates[sellName] ?: error("something went wrong")
        val baseRate = BigDecimal(conversionAmount.toString()).divide(rate, 2, RoundingMode.DOWN)
        val receiveRate = actual.rates[receiveName] ?: error("something went wrong")

        //expect that from be
        val title = "CurrencyConverted"
        val result = baseRate.multiply(receiveRate).setScale(2, RoundingMode.DOWN)
        val commission = fee?.let { "Commission Fee $it $sellName." }
        val message = "You have converted $conversionAmount $sellName to $result $receiveName. ${commission.orEmpty()}"
        return ConversionResultModel(title, message, result)
    }

}