package com.atg.data.market

import com.atg.domain.MarketRepository
import com.atg.network.market.MarketService

class MarketRepositoryImpl(private val marketService: MarketService) : MarketRepository {

    override suspend fun currencies(): String =
        marketService.currencies().toString()

}