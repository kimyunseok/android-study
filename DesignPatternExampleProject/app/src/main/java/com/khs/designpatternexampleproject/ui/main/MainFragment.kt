package com.khs.designpatternexampleproject.ui.main

import com.khs.designpatternexampleproject.R
import com.khs.designpatternexampleproject.databinding.FragmentMainBinding
import com.khs.designpatternexampleproject.ui.mvc.MVCFragment
import com.khs.designpatternexampleproject.ui.base.BaseFragment
import com.khs.designpatternexampleproject.ui.mvvm.MVVMFragment

class MainFragment: BaseFragment<FragmentMainBinding>() {

    override val layoutId: Int = R.layout.fragment_main

    override fun init() {
        setBtnClickListener()
    }

    private fun setBtnClickListener() {
        binding.mvcBtn.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, MVCFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.mvvmBtn.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, MVVMFragment())
                .addToBackStack(null)
                .commit()
        }
    }

}