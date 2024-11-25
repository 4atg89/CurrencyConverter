package com.atg.ui.converter

import android.annotation.SuppressLint
import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.EditText
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.atg.base.BaseFragment
import com.atg.base.extension.dimen
import com.atg.base.listeners.clicks
import com.atg.base.listeners.textChangeDebounce
import com.atg.base.listeners.throttleClick
import com.atg.converter.R
import com.atg.converter.databinding.FragmentConverterBinding
import com.zattoo.movies.base.extensions.bind
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class ConverterFragment : BaseFragment<FragmentConverterBinding>(R.layout.fragment_converter) {

    private val viewModel: ConverterViewModel by viewModel()
    private val adapter = BalanceAdapter()
    private val decimalFormat = DecimalFormat("0.00", DecimalFormatSymbols(Locale.US))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val padding = view.dimen(R.dimen.item_padding)
        binding.balance.addItemDecoration(ListPaddingDecoration.horizontal(padding))
        binding.balance.adapter = adapter

        binding.sellCurrency.bindInput()

        bind(viewModel.state, ::bind)
        bind(viewModel.effect, ::sideEffect)

        viewModel.process(ConverterAction.SubscribeAction)
    }

    private fun EditText.bindInput() {
        filters = arrayOf(CurrencyInputFilter(), InputFilter.LengthFilter(10))
        textChangeDebounce(debounce = 1000) { updateExchangeData() }
    }

    private fun bind(state: ConverterModelState) {
        adapter.submitList(state.balance)

        binding.sellCurrency.setText(decimalFormat.format(state.currentSell.balance))
        binding.selectedSellCurrency.text = state.currentSell.name

        binding.receiveCurrency.text = decimalFormat.format(state.currentReceive.balance)
        binding.selectedReceiveCurrency.text = state.currentReceive.name

        binding.submit.throttleClick { submitExchangeData() }
        binding.selectedSellCurrency.clicks { currencyDialogRequest(true) }
        binding.selectedReceiveCurrency.clicks { currencyDialogRequest(false) }
    }

    private fun currencyDialogRequest(isSell: Boolean) {
        viewModel.process(ConverterAction.ChangeCurrencyAction(isSell))
    }

    private fun updateExchangeData(
        sellName: String = binding.selectedSellCurrency.text.toString(),
        sellBalance: String = binding.sellCurrency.text!!.toString().ifEmpty { "0.0" },
        receiveName: String = binding.selectedReceiveCurrency.text.toString(),
        receiveBalance: String = binding.receiveCurrency.text.toString(),
    ) {
        val sell = BalanceAppModel(sellName, sellBalance.toFloat())
        val receive = BalanceAppModel(receiveName, receiveBalance.toFloat())
        viewModel.process(ConverterAction.NewExchangeDataAction(sell = sell, receive = receive))
    }

    private fun submitExchangeData() {
        val sellName: String = binding.selectedSellCurrency.text.toString()
        val sellBalance: String = binding.sellCurrency.text!!.toString().ifEmpty { "0.0" }
        val receiveName: String = binding.selectedReceiveCurrency.text.toString()
        val receiveBalance: String = binding.receiveCurrency.text.toString()
        val sell = BalanceAppModel(sellName, sellBalance.toFloat())
        val receive = BalanceAppModel(receiveName, receiveBalance.toFloat())
        viewModel.process(ConverterAction.SubmitCurrencyAction(sell = sell, receive = receive))
    }

    private fun sideEffect(effect: Effect) = when(effect){
        is Effect.CurrencyList -> effect.showCurrencyDialog()
        is Effect.ExchangeResult -> effect.showExchangeResult()
    }

    private fun Effect.ExchangeResult.showExchangeResult() {
        MaterialDialog(requireContext()).show {
            title(text = title)
            message(text = message)
            positiveButton(R.string.converter_done)
        }
    }

    @SuppressLint("CheckResult")
    private fun Effect.CurrencyList.showCurrencyDialog() {
        MaterialDialog(requireContext()).show {
            message(R.string.converter_select_currency)
            listItems(items = currencies) { _, index, text ->
                val result = text.toString()
                if (isSell) updateExchangeData(sellName = result)
                else updateExchangeData(receiveName = result)
            }
        }
    }

    override fun onDestroyView() {
        binding.balance.adapter = null
        super.onDestroyView()
    }
}