package com.atg.domain

interface MarketInteractor {
    fun removeLater(): String
}

class MarketInteractorImpl(private val marketRepository: MarketRepository) : MarketInteractor {

    override fun removeLater(): String {
        return marketRepository.removeLater()
    }
}