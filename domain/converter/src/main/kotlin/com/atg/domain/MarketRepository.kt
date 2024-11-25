package com.atg.domain

interface MarketRepository {
    suspend fun currencies(): CurrencyModel
    suspend fun exchange(sellName: String, conversionAmount: Float, receiveName: String, fee: Float?): ConversionResultModel
}