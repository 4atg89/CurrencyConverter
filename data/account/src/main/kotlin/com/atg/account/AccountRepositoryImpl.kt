package com.atg.account

import com.atg.domain.account.AccountRepository
import com.atg.storage.api.ExchangerPrefs

internal class AccountRepositoryImpl(
    private val exchangerPrefs: ExchangerPrefs // I think on the real app it should be on the backend
) : AccountRepository {

    override var balance: Map<String, Float>
        get() = exchangerPrefs.exchanges
        set(value) { exchangerPrefs.exchanges = value }

    override var operationCount: Int
        get() = exchangerPrefs.operationCount
        set(value) { exchangerPrefs.operationCount = value }
}