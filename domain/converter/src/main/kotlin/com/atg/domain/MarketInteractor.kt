package com.atg.domain

interface MarketInteractor {
    suspend fun currencies(): CurrencyModel
    suspend fun exchange(sellName: String, conversionAmount: Float, receiveName: String): ConversionResultModel
}

class MarketInteractorImpl(private val marketRepository: MarketRepository) : MarketInteractor {

    override suspend fun currencies(): CurrencyModel =
        marketRepository.currencies()

    override suspend fun exchange(sellName: String, conversionAmount: Float, receiveName: String): ConversionResultModel {
        return marketRepository.exchange(sellName, conversionAmount, receiveName, 0f)
    }
}