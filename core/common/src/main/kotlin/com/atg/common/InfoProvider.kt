package com.atg.common

interface InfoProvider {
    val isDebug: Boolean
    val baseUrl: String
    val appName: String
}