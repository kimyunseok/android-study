package com.khs.aacviewmodelandrecommandedarchitectureexampleproject.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<T: ViewDataBinding>: Fragment() {
    lateinit var viewDataBinding: T
    abstract var layoutId: Int
    abstract val viewModel: ViewModel
    // lateinit var를 사용해서 by lazy를 사용하지 않고 초기화해서 사용할 수도 있다.

    abstract fun init()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewDataBinding.lifecycleOwner = viewLifecycleOwner

        init()

        return viewDataBinding.root
    }
}