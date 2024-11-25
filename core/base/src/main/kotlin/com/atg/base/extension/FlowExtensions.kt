package com.atg.base.extension

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> Flow<T>.throttleFirst(millis: Int): Flow<T> = flow {
    var emittedAt = 0L
    collect { upstream ->
        val currentTime = System.currentTimeMillis()
        val mayEmit = currentTime - emittedAt > millis
        if (mayEmit) {
            emittedAt = currentTime
            emit(upstream)
        }
    }
}
