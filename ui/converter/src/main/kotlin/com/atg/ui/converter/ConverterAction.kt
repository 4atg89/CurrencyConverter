package com.atg.ui.converter

sealed interface ConverterAction {
    data object SubscribeAction : ConverterAction
    class ChangeCurrencyAction(val isSell: Boolean) : ConverterAction
    class NewExchangeDataAction(val sell: BalanceAppModel, val receive: BalanceAppModel) : ConverterAction
    class SubmitCurrencyAction(val sell: BalanceAppModel, val receive: BalanceAppModel) : ConverterAction
}

sealed interface Effect {
    class CurrencyList(val isSell: Boolean, val currencies: List<String>) : Effect
    class ExchangeResult(val title: String, val message: String) : Effect
}