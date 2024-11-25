package com.atg.base.listeners

import android.view.View
import androidx.annotation.CheckResult
import com.atg.base.extension.throttleFirst
import com.atg.base.extension.viewScope
import com.atg.common.alias.SingleFlowEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.isActive

fun View.throttleClick(
    scope: CoroutineScope = viewScope,
    millis: Int = 2500,
    listener: (View) -> Unit
) = onClick(scope).throttleFirst(millis).onEach { listener.invoke(this) }.launchIn(scope)

fun View.click(scope: CoroutineScope = viewScope, listener: (View) -> Unit) =
    onClick(scope).take(1).onEach { listener.invoke(this) }.launchIn(scope)

fun View.clicks(scope: CoroutineScope = viewScope, listener: (View) -> Unit) =
    onClick(scope).onEach { listener.invoke(this) }.launchIn(scope)

fun View.onClick(scope: CoroutineScope = viewScope): Flow<Unit> = SingleFlowEvent<Unit>().apply {
    setOnClickListener(listener(scope) { tryEmit(Unit) })
    onCompletion { setOnClickListener(null) }
}

@CheckResult
private fun listener(scope: CoroutineScope, emitter: () -> Unit) = View.OnClickListener {
    if (scope.isActive) emitter.invoke()
}
