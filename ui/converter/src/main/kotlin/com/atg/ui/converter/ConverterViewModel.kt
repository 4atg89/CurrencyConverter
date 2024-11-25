package com.atg.ui.converter

import androidx.lifecycle.AtomicReference
import com.atg.base.BaseViewModelImpl
import com.atg.common.alias.SingleFlowEvent
import com.atg.domain.MarketInteractor
import com.atg.domain.account.AccountInteractor
import com.atg.domain.account.AccountRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import java.math.BigDecimal
import java.math.RoundingMode

class ConverterViewModel(
    private val marketInteractor: MarketInteractor,
    private val accountRepository: AccountInteractor
) : BaseViewModelImpl() {

    private val _state = MutableStateFlow(ConverterModelState.default(accountRepository.balance.value))
    val state = _state.asStateFlow()

    private val _effect = SingleFlowEvent<Effect>()
    val effect = _effect.asSharedFlow()

    private val currencies = AtomicReference<Map<String, BigDecimal>>()

    fun process(action: ConverterAction) = when (action) {
        ConverterAction.SubscribeAction -> subscribe()
        is ConverterAction.SubmitCurrencyAction -> action.submit()
        is ConverterAction.NewExchangeDataAction -> action.newExchangeData()
        is ConverterAction.ChangeCurrencyAction -> action.currencyList()
    }

    private fun subscribe() {
        subscribeToCurrency()
        subscribeToBalance()
    }

    private fun subscribeToBalance() {
        accountRepository.balance.onEach {
            _state.value = state.value.copy(balance = it.map { BalanceAppModel(it.key, it.value) })
        }.launchIn(scope)
    }

    private fun subscribeToCurrency() {
        if (currencies.get().isNullOrEmpty().not()) return
        launchOn {
            while (isActive) {
                delay(5000)
                marketInteractor.currencies()
            }
        }
        marketInteractor.currency.onEach { currencies.set(it) }.launchIn(scope)
    }

    private fun ConverterAction.ChangeCurrencyAction.currencyList() {
        _effect.tryEmit(Effect.CurrencyList(isSell, currencies.get().keys.sorted()))
    }

    private fun ConverterAction.NewExchangeDataAction.newExchangeData() {
        if (currencies.get().isEmpty()) return
        val baseRate = sell.asBaseCurrency
        val receiveRate = currencies.get()[receive.name] ?: error("something went wrong")
        val theReceive = receive.copy(balance = baseRate.multiply(receiveRate).toFloat())
        _state.value = state.value.copy(currentSell = sell, currentReceive = theReceive)
    }

    private fun ConverterAction.SubmitCurrencyAction.submit() {
        launchOn {
            val converted = marketInteractor.exchange(sell.name, sell.balance, receive.name)
            val result = converted.convertedAmount.toFloat()
            accountRepository.currencyBought(sell = sell.name to sell.balance, receive = receive.name to result)
            _effect.tryEmit(Effect.ExchangeResult(converted.title, converted.message))
        }
    }

    private val BalanceAppModel.asBaseCurrency: BigDecimal
        get() {
            val rate = currencies.get()[name] ?: error("something went wrong")
            return BigDecimal(balance.toString()).divide(rate, 2, RoundingMode.DOWN)
        }
}