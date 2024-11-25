package com.atg.domain

interface MarketRepository {
    suspend fun currencies(): CurrencyModel
}