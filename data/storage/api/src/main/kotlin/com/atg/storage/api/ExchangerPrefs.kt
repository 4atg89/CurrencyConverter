package com.atg.storage.api

import kotlinx.coroutines.flow.StateFlow

interface ExchangerPrefs {
    val balance: StateFlow<Map<String, Float>>
    val operationCount: Int
    fun currencyBought(sell: Pair<String, Float>, receive: Pair<String, Float>)
}