package com.atg.currencyconverter.di

import com.atg.base.Initializer
import com.atg.common.InfoProvider
import com.atg.currencyconverter.AppInfoProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module
import timber.log.Timber

val appModule = module {
    single<InfoProvider> { AppInfoProvider(get()) }
    single(named("crashlytics")) { crashlytics(get()) }
    single(named("timber")) { timber(get()) }
}

private fun timber(info: InfoProvider): Initializer = Initializer {
    if (info.isDebug) Timber.plant(Timber.DebugTree()) //else plant own tree with crash logs
}

private fun crashlytics(info: InfoProvider): Initializer = Initializer {
//    if (info.isDebug) init crashlytics
}