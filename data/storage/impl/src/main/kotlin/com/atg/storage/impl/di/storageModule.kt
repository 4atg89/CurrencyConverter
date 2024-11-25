package com.atg.storage.impl.di

import android.content.Context
import com.atg.storage.api.ExchangerPrefs
import com.atg.storage.impl.ExchengerPrefsImpl
import org.koin.dsl.module

private const val EXCHANGE = "ExchangePrefs"

val storageModule = module {
    single<ExchangerPrefs> {
        ExchengerPrefsImpl(get<Context>().getSharedPreferences(EXCHANGE, Context.MODE_PRIVATE))
    }
}