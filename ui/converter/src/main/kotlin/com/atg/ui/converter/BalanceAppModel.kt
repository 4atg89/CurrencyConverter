package com.atg.ui.converter

data class ConverterModelState(
    val balance: List<BalanceAppModel>,
    val currentSell: BalanceAppModel,
    val currentReceive: BalanceAppModel,
    val inputEnabled: Boolean
) {
    companion object {
        fun default(balance: Map<String, Float>) = ConverterModelState(
            balance = balance.map { BalanceAppModel(it.key, it.value) },
            BalanceAppModel("EUR", 0f),
            BalanceAppModel("USD", 0f),
            false
        )
    }
}

data class BalanceAppModel(val name: String, val balance: Float)

