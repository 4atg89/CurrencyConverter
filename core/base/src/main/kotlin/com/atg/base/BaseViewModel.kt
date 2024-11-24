package com.atg.base

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atg.common.alias.SingleFlowEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlin.coroutines.CoroutineContext

interface BaseViewModel {
    val errorsStream: SharedFlow<Throwable>
    val scope: CoroutineScope
}

abstract class BaseViewModelImpl : BaseViewModel, ViewModel() {

    override val errorsStream = SingleFlowEvent<Throwable>()

    override val scope: CoroutineScope get() = viewModelScope + handler

    protected val handler = CoroutineExceptionHandler { coroutineContext, exception ->
        handleException(coroutineContext, exception)
    }

    protected fun handleException(coroutineContext: CoroutineContext, exception: Throwable) {
        errorsStream.tryEmit(exception)
    }

    protected fun launchOn(block: suspend CoroutineScope.() -> Unit): Job =
        scope.launch(block = block)

    protected fun <T> asyncOn(block: suspend CoroutineScope.() -> T): Deferred<T> =
        scope.async(block = block)

}