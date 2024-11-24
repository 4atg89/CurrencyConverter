package com.atg.currencyconverter

import android.app.Application
import com.atg.common.InfoProvider

class AppInfoProvider(app: Application) : InfoProvider {
    override val isDebug: Boolean = BuildConfig.DEBUG
    override val baseUrl: String = BuildConfig.BASE_URL
    override val appName: String = app.packageName
}