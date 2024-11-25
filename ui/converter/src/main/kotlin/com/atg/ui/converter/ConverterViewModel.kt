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
            val map = mapOf("AED" to 4.147043,"AFN" to 118.466773,"ALL" to 120.73174,"AMD" to 545.483468,"ANG" to 2.035477,"AOA" to 623.962579,"ARS" to 116.396925,"AUD" to 1.57676,"AWG" to 2.032821,"AZN" to 1.895163,"BAM" to 1.951459,"BBD" to 2.280333,"BDT" to 96.872638,"BGN" to 1.952581,"BHD" to 0.425669,"BIF" to 2253.093736,"BMD" to 1.129031,"BND" to 1.530499,"BOB" to 7.798334,"BRL" to 6.445617,"BSD" to 1.129391,"BTC" to 2.6156179e-5,"BTN" to 83.913403,"BWP" to 13.318607,"BYN" to 2.918863,"BYR" to 22129.014412,"BZD" to 2.276542,"CAD" to 1.445555,"CDF" to 2263.707335,"CHF" to 1.037789,"CLF" to 0.03431,"CLP" to 946.71533,"CNY" to 7.198254,"COP" to 4548.494719,"CRC" to 725.029021,"CUC" to 1.129031,"CUP" to 29.919331,"CVE" to 110.017623,"CZK" to 24.656241,"DJF" to 201.063247,"DKK" to 7.438803,"DOP" to 64.669308,"DZD" to 157.117122,"EGP" to 17.742178,"ERN" to 16.935558,"ETB" to 56.010148,"EUR" to 1,"FJD" to 2.399167,"FKP" to 0.851718,"GBP" to 0.835342,"GEL" to 3.494348,"GGP" to 0.851718,"GHS" to 6.97385,"GIP" to 0.851718,"GMD" to 59.609858,"GNF" to 10416.455146,"GTQ" to 8.718696,"GYD" to 236.28081,"HKD" to 8.806501,"HNL" to 27.726393,"HRK" to 7.522063,"HTG" to 115.219987,"HUF" to 363.074072,"IDR" to 16256.0756,"ILS" to 3.521099,"IMP" to 0.851718,"INR" to 84.06711,"IQD" to 1648.197205,"IRR" to 47701.574046,"ISK" to 146.819264,"JEP" to 0.851718,"JMD" to 173.825768,"JOD" to 0.800527,"JPY" to 130.869977,"KES" to 127.80595,"KGS" to 95.749402,"KHR" to 4602.273972,"KMF" to 490.335222,"KPW" to 1016.128125,"KRW" to 1357.965315,"KWD" to 0.341826,"KYD" to 0.941205,"KZT" to 491.795077,"LAK" to 12682.938295,"LBP" to 1707.874932,"LKR" to 228.134954,"LRD" to 164.950073,"LSL" to 17.929034,"LTL" to 3.333736,"LVL" to 0.682939,"LYD" to 5.197651,"MAD" to 10.455783,"MDL" to 20.15945,"MGA" to 4487.678598,"MKD" to 61.477311,"MMK" to 2008.074357,"MNT" to 3227.205877,"MOP" to 9.066072,"MRO" to 403.063997,"MUR" to 49.508135,"MVR" to 17.443978,"MWK" to 922.043265,"MXN" to 23.403578,"MYR" to 4.756044,"MZN" to 72.066134,"NAD" to 17.934686,"NGN" to 466.062578,"NIO" to 39.986153,"NOK" to 10.054905,"NPR" to 134.270657,"NZD" to 1.675177,"OMR" to 0.43408,"PAB" to 1.129371,"PEN" to 4.47123,"PGK" to 4.016376,"PHP" to 57.778153,"PKR" to 199.649788,"PLN" to 4.581315,"PYG" to 7790.689469,"QAR" to 4.110825,"RON" to 4.946966,"RSD" to 117.605521,"RUB" to 86.49171,"RWF" to 1171.766392,"SAR" to 4.240189,"SBD" to 9.123559,"SCR" to 15.83372,"SDG" to 493.951724,"SEK" to 10.33977,"SGD" to 1.53581,"SHP" to 1.555125,"SLL" to 12724.18312,"SOS" to 661.611904,"SRD" to 24.094088,"STD" to 23368.669389,"SVC" to 9.881999,"SYP" to 2836.683113,"SZL" to 17.9123,"THB" to 37.74317,"TJS" to 12.760798,"TMT" to 3.95161,"TND" to 3.246528,"TOP" to 2.571034,"TRY" to 15.612274,"TTD" to 7.678802,"TWD" to 31.218506,"TZS" to 2605.804869,"UAH" to 31.018778,"UGX" to 3998.045715,"USD" to 1.129031,"UYU" to 50.399435,"UZS" to 12218.550541,"VEF" to 241421024074.42,"VND" to 25691.672829,"VUV" to 127.865795,"WST" to 2.935675,"XAF" to 654.502727,"XAG" to 0.050046,"XAU" to 0.000626,"XCD" to 3.051263,"XDR" to 0.808703,"XOF" to 654.502727,"XPF" to 119.169197,"YER" to 282.540438,"ZAR" to 18.00901,"ZMK" to 10162.625635,"ZMW" to 18.934429,"ZWL" to 363.547633)
            currencies.value =
                CurrencyModel("EUR", map.map { it.key to BigDecimal(it.value.toString()) }.toMap())
//                marketInteractor.currencies()
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