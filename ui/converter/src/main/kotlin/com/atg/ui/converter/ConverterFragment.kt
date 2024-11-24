package com.atg.ui.converter

import android.os.Bundle
import android.view.View
import com.atg.base.BaseFragment
import com.atg.converter.R
import com.atg.converter.databinding.FragmentConverterBinding
import com.zattoo.movies.base.extensions.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConverterFragment : BaseFragment<FragmentConverterBinding>(R.layout.fragment_converter) {

    private val viewModel : ConverterViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind(viewModel.state, binding.text::setText)
        viewModel.fetch()
    }

}