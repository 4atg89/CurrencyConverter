package com.atg.domain

import com.atg.common.DiscountStrategy
import kotlinx.coroutines.flow.StateFlow
import java.math.BigDecimal
import java.math.RoundingMode

interface MarketInteractor {
    val currency: StateFlow<Map<String, BigDecimal>>
    suspend fun currencies()
    suspend fun exchange(sellName: String, sellBalance: Float, conversionAmount: Float, receiveName: String): ConversionResultModel
}

class MarketInteractorImpl(
    private val marketRepository: MarketRepository,
    private val feeStrategy: DiscountStrategy
) : MarketInteractor {

    override val currency: StateFlow<Map<String, BigDecimal>>
        get() = marketRepository.currency

    override suspend fun currencies() =
        marketRepository.currencies()

    override suspend fun exchange(sellName: String, sellBalance: Float, conversionAmount: Float, receiveName: String): ConversionResultModel {
        val discount = feeStrategy.evaluateDiscount(sellName, conversionAmount, receiveName)
        val fee = BigDecimal("0.7").subtract(BigDecimal(discount.toString()))
            .multiply(BigDecimal(conversionAmount.toString()))
            .divide(BigDecimal("100"), 2, RoundingMode.DOWN)
            .toFloat()
        if (sellBalance - fee - conversionAmount < 0f) error("not enough money on the account")
        return marketRepository.exchange(sellName, conversionAmount, receiveName, fee)
    }

    fun BigDecimal.getPercent(percent: BigDecimal, scale: Int = 2, roundingMode: RoundingMode = RoundingMode.HALF_UP): BigDecimal {
        return this.multiply(percent).divide(BigDecimal("100"), scale, roundingMode)
    }
}