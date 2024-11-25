package com.atg.storage.api

interface ExchangerPrefs {
    var exchanges: Map<String, Float>
    var operationCount: Int
}