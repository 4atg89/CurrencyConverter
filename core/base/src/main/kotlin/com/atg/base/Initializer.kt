package com.atg.base

import android.app.Application

fun interface Initializer {
    fun initialize(app: Application)
}