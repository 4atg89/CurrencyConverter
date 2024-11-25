package com.atg.account.di

import com.atg.account.AccountRepositoryImpl
import com.atg.domain.account.AccountRepository
import org.koin.dsl.module

val accountModule = module {
    single<AccountRepository> { AccountRepositoryImpl(get()) }
}