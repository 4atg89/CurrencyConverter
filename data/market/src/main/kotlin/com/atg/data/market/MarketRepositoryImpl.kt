package com.atg.data.market

import com.atg.domain.CurrencyModel
import com.atg.domain.MarketRepository
import com.atg.network.market.MarketCurrencyDTO
import com.atg.network.market.MarketService

class MarketRepositoryImpl(private val marketService: MarketService) : MarketRepository {

    override suspend fun currencies(): CurrencyModel =
        marketService.currencies().map()

    private fun MarketCurrencyDTO.map() =
        CurrencyModel(base, rates)
}