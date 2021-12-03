package com.khs.designpatternexampleproject.ui.main

import com.khs.designpatternexampleproject.R
import com.khs.designpatternexampleproject.databinding.FragmentMainBinding
import com.khs.designpatternexampleproject.ui.mvc.MVCFragment
import com.khs.designpatternexampleproject.ui.base.BaseFragment
import com.khs.designpatternexampleproject.ui.mvp.MVPFragment
import com.khs.designpatternexampleproject.ui.mvvm.MVVMFragment
import com.khs.designpatternexampleproject.util.FragmentTransitionManager

/**
 * 디자인 패턴(MVC, MVP, MVVM)에서의 MV는 모두 동일합니다.
 * Model : 데이터를 저장하는 계층입니다. Local DB / Server DB에서 데이터에 접근하는 모든 로직을 통틀어서 Model이라고 합니다.
 *
 * View : 데이터를 시각화하는 곳입니다. 즉, UI를 뜻합니다.
 */
class MainFragment: BaseFragment<FragmentMainBinding>() {

    override val layoutId: Int = R.layout.fragment_main

    override fun init() {
        setBtnClickListener()
    }

    private fun setBtnClickListener() {
        binding.mvcBtn.setOnClickListener {
            FragmentTransitionManager().changeFragmentOnActivity(requireActivity(), R.id.main_container, MVCFragment(), true)
        }

        binding.mvpBtn.setOnClickListener {
            FragmentTransitionManager().changeFragmentOnActivity(requireActivity(), R.id.main_container, MVPFragment(), true)
        }

        binding.mvvmBtn.setOnClickListener {
            FragmentTransitionManager().changeFragmentOnActivity(requireActivity(), R.id.main_container, MVVMFragment(), true)
        }
    }

}