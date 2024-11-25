package com.atg.domain.account

import com.atg.common.DiscountStrategy

class EachTenExchangeFree(private val accountRepository: AccountRepository): DiscountStrategy {

    override fun evaluateDiscount(sellName: String, conversionAmount: Float, receiveName: String): Float {
        val operation = accountRepository.operationCount
        if (operation > 0 && operation % 10 == 0) return 0.5f
        return 0f
    }

}