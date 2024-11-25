package com.atg.domain

import kotlinx.coroutines.flow.StateFlow
import java.math.BigDecimal

interface MarketInteractor {
    val currency: StateFlow<Map<String, BigDecimal>>
    suspend fun currencies()
    suspend fun exchange(sellName: String, conversionAmount: Float, receiveName: String): ConversionResultModel
}

class MarketInteractorImpl(private val marketRepository: MarketRepository) : MarketInteractor {

    override val currency: StateFlow<Map<String, BigDecimal>>
        get() = marketRepository.currency

    override suspend fun currencies() =
        marketRepository.currencies()

    override suspend fun exchange(sellName: String, conversionAmount: Float, receiveName: String): ConversionResultModel {
        return marketRepository.exchange(sellName, conversionAmount, receiveName, 0f)
    }
}