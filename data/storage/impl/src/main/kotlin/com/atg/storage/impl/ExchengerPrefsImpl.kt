package com.atg.storage.impl

import android.content.SharedPreferences
import android.icu.math.BigDecimal
import com.atg.storage.api.ExchangerPrefs
import kotlinx.coroutines.flow.MutableStateFlow

internal class ExchengerPrefsImpl(private val prefs: SharedPreferences) : ExchangerPrefs {

    init { if (operationCount == -5) prefs.edit { putFloat("EUR", 1000f) } }

    override val balance = MutableStateFlow(savedBalance)

    override var operationCount: Int
        get() = prefs.getInt(ExchengerPrefsImpl::class.simpleName, -5)
        private set(value) { prefs.edit { putInt(ExchengerPrefsImpl::class.simpleName, value) } }

    override fun currencyBought(sell: Pair<String, Float>, receive: Pair<String, Float>) {

        val newSell = BigDecimal(prefs.getFloat(sell.first, 0f).toString())
            .subtract(BigDecimal(sell.second.toString()))
            .setScale(2, BigDecimal.ROUND_DOWN)
            .toFloat()
        if (newSell < 0f) error("pass only positive")

        val newReceive = BigDecimal(prefs.getFloat(receive.first, 0f).toString())
            .add(BigDecimal(receive.second.toString()))
            .setScale(2, BigDecimal.ROUND_DOWN)
            .toFloat()

        prefs.edit(commit = true) {
            if (newSell == 0f) remove(sell.first) else putFloat(sell.first, newSell)
            putFloat(receive.first, newReceive)
        }
        operationCount += 1
        balance.value = savedBalance
    }

    private val savedBalance: Map<String, Float>
        get() = prefs.all
            .filterValues { it is Float }
            .mapValues { it.value as Float }
}