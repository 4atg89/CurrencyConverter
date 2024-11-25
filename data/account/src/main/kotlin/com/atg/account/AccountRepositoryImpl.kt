package com.atg.account

import com.atg.domain.account.AccountRepository
import com.atg.storage.api.ExchangerPrefs

internal class AccountRepositoryImpl(
    private val exchangerPrefs: ExchangerPrefs // I think on the real app it should be on the backend
) : AccountRepository {

    override val balance = exchangerPrefs.balance

    override val operationCount: Int
        get() = exchangerPrefs.operationCount

    override fun currencyBought(sell: Pair<String, Float>, receive: Pair<String, Float>) {
        exchangerPrefs.currencyBought(sell, receive)
    }
}