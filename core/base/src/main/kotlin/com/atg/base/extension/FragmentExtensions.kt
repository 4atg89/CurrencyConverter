package com.zattoo.movies.base.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun <T> Fragment.bind(flow: Flow<T>, func: (T) -> Unit) {
    flow.onEach { it?.let(func) }.launchIn(viewLifecycleOwner.lifecycleScope)
}