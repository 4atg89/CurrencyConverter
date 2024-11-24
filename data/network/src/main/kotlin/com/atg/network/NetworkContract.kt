package com.atg.network

object NetworkContract {

    const val API = "tasks/api"

    object Market {
        /**
         * Get method
         * @see com.atg.network.market.MarketCurrencyDTO
         */
        const val MARKET_CURRENCY = "$API/currency-exchange-rates"

    }

}