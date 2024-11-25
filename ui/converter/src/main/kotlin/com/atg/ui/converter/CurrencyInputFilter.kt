package com.atg.ui.converter

import android.text.InputFilter
import android.text.Spanned

class CurrencyInputFilter: InputFilter {

    private val regex = Regex("^([1-9]\\d*|0)?(\\.\\d{0,2})?\$")

    override fun filter(
        source: CharSequence?,
        start: Int, end: Int,
        dest: Spanned?,
        dstart: Int, dend: Int
    ): CharSequence? {
        val destText = dest?.toString().orEmpty()
        val newText = destText.substring(0, dstart) + source + destText.substring(dend)

        if (!newText.matches(regex)) return ""
        return null
    }
}