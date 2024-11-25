package com.atg.ui.converter

import com.atg.base.BaseViewModelImpl
import com.atg.common.alias.SingleFlowEvent
import com.atg.domain.CurrencyModel
import com.atg.domain.MarketInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import java.math.BigDecimal
import java.math.RoundingMode

class ConverterViewModel(private val marketInteractor: MarketInteractor) : BaseViewModelImpl() {

    private val _state = MutableStateFlow(ConverterModelState.default)
    val state = _state.asStateFlow()

    private val _effect = SingleFlowEvent<Effect>()
    val effect = _effect.asSharedFlow()

    private val currencies = MutableStateFlow(CurrencyModel("EUR", emptyMap()))

    fun process(action: ConverterAction) = when (action) {
        ConverterAction.SubscribeAction -> subscribe()
        is ConverterAction.SubmitCurrencyAction -> action.submit()
        is ConverterAction.NewExchangeDataAction -> action.newExchangeData()
        is ConverterAction.ChangeCurrencyAction -> action.currencyList()
    }

    private fun subscribe() {
        launchOn {
            currencies.value = marketInteractor.currencies()
        }
    }

    private fun ConverterAction.ChangeCurrencyAction.currencyList() {
        _effect.tryEmit(Effect.CurrencyList(isSell, currencies.value.rates.keys.sorted()))
    }

    private fun ConverterAction.NewExchangeDataAction.newExchangeData() {
        val baseRate = sell.asBaseCurrency
        val receiveRate = currencies.value.rates[receive.name] ?: error("something went wrong")
        val theReceive = receive.copy(balance = baseRate.multiply(receiveRate).toFloat())
        _state.value = state.value.copy(currentSell = sell, currentReceive = theReceive)
    }

    private fun ConverterAction.SubmitCurrencyAction.submit() {
        val title = "CurrencyConverted"
        val message = "You have converted ${sell.balance} ${sell.name} to addResultLater"
        _effect.tryEmit(Effect.ExchangeResult(title, message))
    }

    private val BalanceAppModel.asBaseCurrency: BigDecimal
        get() {
            val rate = currencies.value.rates[name] ?: error("something went wrong")
            return BigDecimal(balance.toString()).divide(rate ,2, RoundingMode.HALF_UP)
        }
}