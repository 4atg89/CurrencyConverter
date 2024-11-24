package com.atg.network.market

import com.atg.network.NetworkContract
import retrofit2.http.GET

interface MarketService {

    @GET(NetworkContract.Market.MARKET_CURRENCY)
    suspend fun currencies(): MarketCurrencyDTO

}