package com.khs.retrofitandokhttpexampleproject.ui.main

import android.os.Bundle
import android.util.Log
import com.khs.retrofitandokhttpexampleproject.R
import com.khs.retrofitandokhttpexampleproject.databinding.FragmentMainBinding
import com.khs.retrofitandokhttpexampleproject.ui.BaseFragment
import com.khs.retrofitandokhttpexampleproject.ui.user.UserProfileFragment
import kotlinx.coroutines.handleCoroutineException

class MainFragment: BaseFragment<FragmentMainBinding>() {

    override val layoutId: Int = R.layout.fragment_main

    override fun init() {
        viewDataBinding.handle = "" // 양방향 데이터바인딩 값 초기화
        
        setUpBtnListener()
    }

    private fun setUpBtnListener() {
        viewDataBinding.searchBtn.setOnClickListener {

            val fragment = childFragmentManager.findFragmentById(viewDataBinding.userProfileContainer.id) as UserProfileFragment?

            if(fragment == null) {
                val bundle = Bundle().apply {
                    putString("handle", viewDataBinding.handle)
                }
                childFragmentManager
                    .beginTransaction()
                    .replace(viewDataBinding.userProfileContainer.id,
                        UserProfileFragment().apply {arguments = bundle}
                    )
                    .commit()
            } else {
                fragment.userDataViewModel.getUserData(viewDataBinding.handle.toString())
            }

        }
    }

}