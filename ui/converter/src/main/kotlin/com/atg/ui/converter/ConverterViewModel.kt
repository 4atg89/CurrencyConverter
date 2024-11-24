package com.atg.ui.converter

import com.atg.base.BaseViewModelImpl
import com.atg.common.alias.SingleFlowEvent
import com.atg.domain.MarketInteractor

class ConverterViewModel(private val marketInteractor: MarketInteractor) : BaseViewModelImpl() {

    val state = SingleFlowEvent<String>()

    fun fetch() {
        launchOn {
            val a = marketInteractor.currencies()
            state.tryEmit(a)
        }
    }
}