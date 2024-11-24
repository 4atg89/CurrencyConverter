package com.atg.domain

interface MarketInteractor {
    suspend fun currencies(): String
}

class MarketInteractorImpl(private val marketRepository: MarketRepository) : MarketInteractor {

    override suspend fun currencies(): String {
        return marketRepository.currencies()
    }
}