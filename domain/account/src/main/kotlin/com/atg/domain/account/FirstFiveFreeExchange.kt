package com.atg.domain.account

import com.atg.common.DiscountStrategy

class FirstFiveFreeExchange(private val accountRepository: AccountRepository) : DiscountStrategy {

    override fun evaluateDiscount(sellName: String, conversionAmount: Float, receiveName: String): Float {
        if (accountRepository.operationCount < 0) return 0.7f
        return 0f
    }

}