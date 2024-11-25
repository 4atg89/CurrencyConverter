package com.atg.ui.converter

data class ConverterModelState(
    val balance: List<BalanceAppModel>,
    val currentSell: BalanceAppModel,
    val currentReceive: BalanceAppModel
) {
    //todo remove
    companion object {
        val default = ConverterModelState(
            emptyList(),
            BalanceAppModel("EUR", 0f),
            BalanceAppModel("USD", 0f)
        )
    }
}

data class BalanceAppModel(val name: String, val balance: Float)

