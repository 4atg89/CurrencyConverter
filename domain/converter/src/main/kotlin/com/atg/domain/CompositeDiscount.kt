package com.atg.domain

import com.atg.common.DiscountStrategy

class CompositeDiscount(private val discounts: List<DiscountStrategy>): DiscountStrategy {

    override fun evaluateDiscount(sellName: String, conversionAmount: Float, receiveName: String): Float {
        var result = 0f
        discounts.forEach {
            result += it.evaluateDiscount(sellName, conversionAmount, receiveName)
            if (result > 0.7f) {
                result = 0.7f
                return@forEach
            }
        }
        return result
    }
}