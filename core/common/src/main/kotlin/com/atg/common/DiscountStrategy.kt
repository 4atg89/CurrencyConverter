package com.atg.common

interface DiscountStrategy {
    fun evaluateDiscount(sellName: String, conversionAmount: Float, receiveName: String): Float
}
