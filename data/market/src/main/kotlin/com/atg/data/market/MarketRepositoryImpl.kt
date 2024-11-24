package com.atg.data.market

import com.atg.domain.MarketRepository

class MarketRepositoryImpl : MarketRepository {
    override fun removeLater(): String {
        return "test data"
    }
}