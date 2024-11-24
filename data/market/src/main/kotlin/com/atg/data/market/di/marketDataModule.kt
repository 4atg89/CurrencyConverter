package com.atg.data.market.di

import com.atg.data.market.MarketRepositoryImpl
import com.atg.domain.MarketRepository
import org.koin.dsl.module

val marketDataModule = module {
    single<MarketRepository> { MarketRepositoryImpl(get()) }
}