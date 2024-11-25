package com.atg.base.extension

import android.view.View
import androidx.annotation.DimenRes
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope

fun View.dimen(@DimenRes id: Int) = context.resources.getDimensionPixelSize(id)

val View.viewScope
    get() = requireNotNull(findViewTreeLifecycleOwner()) {
        "viewTree is not attached yet"
    }.lifecycleScope

