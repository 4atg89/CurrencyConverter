package com.atg.domain

import java.math.BigDecimal

class CurrencyModel(
    val base: String,
    val rates: Map<String, BigDecimal>,
)

class ConversionResultModel(
    val title: String,
    val message: String,
    val convertedAmount: BigDecimal
)