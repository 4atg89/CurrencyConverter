package com.atg.ui.converter

import com.atg.base.BaseViewModelImpl
import com.atg.common.alias.SingleFlowEvent
import com.atg.data.market.MarketRepositoryImpl
import com.atg.domain.MarketInteractor
import com.atg.domain.MarketInteractorImpl

class ConverterViewModel(
    private val marketInteractor: MarketInteractor =
        MarketInteractorImpl(MarketRepositoryImpl())
) : BaseViewModelImpl() {

    val state = SingleFlowEvent<String>()

    fun fetch() {
        state.tryEmit(marketInteractor.removeLater())
    }
}