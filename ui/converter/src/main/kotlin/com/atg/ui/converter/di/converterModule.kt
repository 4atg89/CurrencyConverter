package com.atg.ui.converter.di

import com.atg.account.di.accountModule
import com.atg.data.market.di.marketDataModule
import com.atg.domain.MarketInteractor
import com.atg.domain.MarketInteractorImpl
import com.atg.domain.account.AccountInteractor
import com.atg.domain.account.AccountInteractorImpl
import com.atg.ui.converter.ConverterViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val converterModule = module {
    includes(marketDataModule, accountModule)
    viewModelOf(::ConverterViewModel)
    factory<MarketInteractor> { MarketInteractorImpl(get()) }
    factory<AccountInteractor> { AccountInteractorImpl(get()) }
}