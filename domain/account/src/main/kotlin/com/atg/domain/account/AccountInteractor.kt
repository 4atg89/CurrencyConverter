package com.atg.domain.account

import kotlinx.coroutines.flow.StateFlow

interface AccountInteractor {
    fun currencyBought(sell: Pair<String, Float>, receive: Pair<String, Float>)
    val balance: StateFlow<Map<String, Float>>
}

class AccountInteractorImpl(private val accountRepository: AccountRepository) : AccountInteractor {

    override fun currencyBought(sell: Pair<String, Float>, receive: Pair<String, Float>) {
        accountRepository.currencyBought(sell = sell, receive = receive)
    }

    override val balance get() = accountRepository.balance
}