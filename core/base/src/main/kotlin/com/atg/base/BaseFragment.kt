package com.atg.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.atg.base.extension.inflate

abstract class BaseFragment<T : ViewBinding> : Fragment {

    constructor(@LayoutRes layoutRes: Int) : super(layoutRes)
    constructor() : super()

    private var _binding: T? = null

    /**
     * throw exception if it is used not between onViewCreated and onViewDestroyed
     */
    protected val binding: T get() =
        requireNotNull(_binding) { "use binding only while fragment's view exists" }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, bundle: Bundle?): View {
        _binding = inflate(inflater, parent)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
