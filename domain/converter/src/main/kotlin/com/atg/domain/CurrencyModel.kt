package com.atg.domain

import java.math.BigDecimal

class ConversionResultModel(
    val title: String,
    val message: String,
    val fee: Float,
    val convertedAmount: BigDecimal
)