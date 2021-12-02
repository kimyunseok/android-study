package com.khs.designpatternexampleproject.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.khs.designpatternexampleproject.ui.main.LoadingDialogFragment

abstract class BaseFragment<T: ViewDataBinding>: Fragment() {
    lateinit var binding: T
    abstract val layoutId: Int
    val loadingDialog = LoadingDialogFragment()

    abstract fun init()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        init()

        return binding.root
    }
}