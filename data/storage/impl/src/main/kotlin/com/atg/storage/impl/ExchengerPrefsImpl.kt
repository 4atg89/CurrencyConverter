package com.atg.storage.impl

import android.content.SharedPreferences
import com.atg.storage.api.ExchangerPrefs

internal class ExchengerPrefsImpl(private val prefs: SharedPreferences) : ExchangerPrefs {

    init { if (operationCount == -5) prefs.edit { putFloat("EUR", 1000f) } }

    override var exchanges: Map<String, Float>
        get() = prefs.all
            .filterValues { it is Float && it > 0f }
            .mapValues { it.value as Float }
        set(value) {
            if (value.isNotEmpty()) operationCount -= 1
            prefs.edit {
                value.forEach {
                    if (it.value == 0f) remove(it.key) else putFloat(it.key, it.value)
                    if (it.value < 0) error("${it.key} is lower then 0")
                }
            }
        }

    override var operationCount: Int
        get() = prefs.getInt(ExchengerPrefsImpl::class.simpleName, -5)
        set(value) { prefs.edit { putInt(ExchengerPrefsImpl::class.simpleName, value) } }

}