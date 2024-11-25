package com.atg.currencyconverter

import android.app.Application
import com.atg.base.Initializer
import com.atg.currencyconverter.di.appModule
import com.atg.network.di.networkModule
import com.atg.storage.impl.di.storageModule
import com.atg.ui.converter.di.converterModule
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class TheApp : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@TheApp)
            modules(appModule, networkModule, storageModule, converterModule)
        }
        getKoin().getAll<Initializer>().forEach { it.initialize(this) }
    }

}