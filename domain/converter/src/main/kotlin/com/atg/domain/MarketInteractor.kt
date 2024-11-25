package com.atg.domain

interface MarketInteractor {
    suspend fun currencies(): CurrencyModel
}

class MarketInteractorImpl(private val marketRepository: MarketRepository) : MarketInteractor {

    override suspend fun currencies(): CurrencyModel =
        marketRepository.currencies()
}