package com.atg.domain

import kotlinx.coroutines.flow.StateFlow
import java.math.BigDecimal

interface MarketRepository {
    val currency: StateFlow<Map<String, BigDecimal>>
    suspend fun currencies()
    suspend fun exchange(sellName: String, conversionAmount: Float, receiveName: String, fee: Float?): ConversionResultModel
}