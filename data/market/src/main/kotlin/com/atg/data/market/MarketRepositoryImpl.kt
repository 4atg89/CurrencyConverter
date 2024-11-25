package com.atg.data.market

import com.atg.domain.ConversionResultModel
import com.atg.domain.MarketRepository
import com.atg.network.market.MarketService
import kotlinx.coroutines.flow.MutableStateFlow
import java.math.BigDecimal
import java.math.RoundingMode

class MarketRepositoryImpl(private val marketService: MarketService) : MarketRepository {

    override val currency = MutableStateFlow<Map<String, BigDecimal>>(emptyMap())

    override suspend fun currencies() {
        //this is just not to ddos be as values same each time
        if (currency.value.isEmpty()) marketService.currencies().let { currency.value = it.rates }
        else currency.value = currency.value
    }

    override suspend fun exchange(sellName: String, conversionAmount: Float, receiveName: String, fee: Float): ConversionResultModel {
        val rates = currency.value
        val rate = rates[sellName] ?: error("something went wrong")
        val baseRate = BigDecimal(conversionAmount.toString()).divide(rate, 2, RoundingMode.DOWN)
        val receiveRate = rates[receiveName] ?: error("something went wrong")

        //expect that from be
        val title = "CurrencyConverted"
        val result = baseRate.multiply(receiveRate).setScale(2, RoundingMode.DOWN)
        val commission = fee.takeIf { it > 0f }?.let { "Commission Fee $it $sellName." }
        val message = "You have converted $conversionAmount $sellName to $result $receiveName.\n ${commission.orEmpty()}"
        return ConversionResultModel(title, message, fee, result)
    }

}