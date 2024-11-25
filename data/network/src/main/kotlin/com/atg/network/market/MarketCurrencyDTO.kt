package com.atg.network.market

import com.atg.network.serializer.BigDecimalAlias
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MarketCurrencyDTO(
    @SerialName("base") val base: String,
    @SerialName("rates") val rates: Map<String, BigDecimalAlias>,
)