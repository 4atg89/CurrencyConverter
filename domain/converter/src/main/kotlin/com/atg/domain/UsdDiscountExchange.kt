package com.atg.domain

import com.atg.common.DiscountStrategy

class UsdDiscountExchange : DiscountStrategy {

    override fun evaluateDiscount(sellName: String, conversionAmount: Float, receiveName: String): Float {
        if(sellName == "USD") return 0.1f
        return 0f
    }
}