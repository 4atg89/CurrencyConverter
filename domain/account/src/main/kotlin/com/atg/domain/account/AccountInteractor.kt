package com.atg.domain.account

interface AccountInteractor {
    var balance: Map<String, Float>
}

class AccountInteractorImpl(private val accountRepository: AccountRepository) : AccountInteractor {
    override var balance: Map<String, Float>
        get() = accountRepository.balance
        set(value) { accountRepository.balance = value }
}