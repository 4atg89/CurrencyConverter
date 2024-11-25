package com.atg.domain.account

import kotlinx.coroutines.flow.StateFlow

interface AccountRepository {
    val balance: StateFlow<Map<String, Float>>
    val operationCount: Int
    fun currencyBought(sell: Pair<String, Float>, receive: Pair<String, Float>)
}