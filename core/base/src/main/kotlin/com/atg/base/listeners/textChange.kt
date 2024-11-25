package com.atg.base.listeners

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.annotation.CheckResult
import com.atg.base.extension.viewScope
import com.atg.common.alias.SingleFlowEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive

fun TextView.textChange(listener: (CharSequence) -> Unit) =
    onTextChanges().drop(1).onEach { listener.invoke(it) }.launchIn(viewScope)

fun TextView.textChangeEager(listener: (CharSequence) -> Unit) =
    onTextChanges().onEach { listener.invoke(it) }.launchIn(viewScope)

fun TextView.textChangeDebounce(debounce: Long, listener: (CharSequence) -> Unit) =
    onTextChanges().drop(1).debounce(debounce).onEach { listener.invoke(it) }.launchIn(viewScope)

fun TextView.textChangeDebounceEager(debounce: Long, listener: (CharSequence) -> Unit) =
    onTextChanges().debounce(debounce).onEach { listener.invoke(it) }.launchIn(viewScope)

fun TextView.afterTextChangeEager(listener: (CharSequence) -> Unit) =
    onAfterTextChanges().onEach { listener.invoke(it) }.launchIn(viewScope)

@CheckResult
fun TextView.onTextChanges(): Flow<String> = MutableStateFlow(text.toString()).apply {
    val listener = listener(viewScope, onTextChanged = { value = it })
    addTextChangedListener(listener)
    onCompletion { removeTextChangedListener(listener) }
}

@CheckResult
fun TextView.onAfterTextChanges(): Flow<String> = SingleFlowEvent<String>().apply {
    val listener = listener(viewScope, afterTextChanged = ::tryEmit)
    addTextChangedListener(listener)
    onCompletion { removeTextChangedListener(listener) }
}

@CheckResult
private fun listener(
    scope: CoroutineScope,
    beforeTextChanged: (String) -> Unit = {},
    onTextChanged: (String) -> Unit = {},
    afterTextChanged: (String) -> Unit = {}
) = object : TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        if (scope.isActive) beforeTextChanged(s.toString())
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (scope.isActive) onTextChanged(s.toString())
    }

    override fun afterTextChanged(s: Editable) {
        if (scope.isActive) afterTextChanged(s.toString())
    }
}