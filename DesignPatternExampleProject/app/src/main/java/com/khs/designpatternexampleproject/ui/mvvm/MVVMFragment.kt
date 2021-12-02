package com.khs.designpatternexampleproject.ui.mvvm

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.khs.designpatternexampleproject.R
import com.khs.designpatternexampleproject.databinding.FragmentUserInfoDataBindingBinding
import com.khs.designpatternexampleproject.ui.base.BaseFragment
import com.khs.designpatternexampleproject.ui.mvvm.repository.UserRepository
import com.khs.designpatternexampleproject.ui.mvvm.viewmodel.UserViewModel
import com.khs.designpatternexampleproject.ui.mvvm.viewmodel.UserViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * MVC에서는 Activity / Fragment가 View의 역할만 수행하게 된다.
 *
 * View와 Model이 완전히 독립성을 유지할 수 있게 된다.
 * View와 MVVM ViewModel간의 관계는 n : n일 수 있다.
 * 하나의 View에서 여러 개의 MVVM ViewModel을 사용할 수 있고, (그러나 하나의 View는 하나의 ViewModel만 사용하는 것이 좋다.)
 * 하나의 MVVM ViewModel은 여러 개의 View에서 사용되어질 수 있다.
 *
 * AAC ViewModel과 같이 사용할 경우, Activity에 하나의 AAC ViewModel을 생성할 수 있다.
 * 즉, 하나의 Activity 위에 여러 개의 Fragment에서 같은 ViewModel을 공유해서 사용할 수 있다.
 *
 * 장점 :
 * 1. Activity / Fragment에서 ViewModel이 Model관련 작업을 수행하므로 View와 Model간에 의존성이 사라지게 된다. 때문에 테스트가 용이하다.
 * 2. Observable한 값(ex. LiveData)을 사용할 경우 UI가 실시간으로 최신값으로 갱신될 수 있다.
 * 3. 데이터 바인딩을 사용하게 되면 UI 관련 소스 코드 로직을 줄일 수 있다.
 *
 * 단점 :
 * 1. 간단한 기능 구현에도 ViewModel, Repository를 사용할 경우 복잡한 구성이 된다.
 * 2. ViewModel에 기능 구현이 많아지면, MVC 패턴의 Controller처럼 복잡한 로직을 가질 수 있게된다.
 * 3. 데이터 바인딩이 의무화가 된다.
 *
 */

class MVVMFragment: BaseFragment<FragmentUserInfoDataBindingBinding>() {
    override val layoutId: Int = R.layout.fragment_user_info_data_binding

    lateinit var viewModelFactory: UserViewModelFactory
    lateinit var userViewModel: UserViewModel

    override fun init() {
        initViewModel()
        setUpObserver()

        getUserInfo()
    }

    private fun initViewModel() {
        viewModelFactory = UserViewModelFactory(UserRepository())
        //userViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(UserViewModel::class.java) - EventWrapper 사용 시.
        userViewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)
        binding.vm = userViewModel // ViewModel을 Layout에 Binding한다.
    }

    private fun setUpObserver() {
        userViewModel.modifyComplete.observe(viewLifecycleOwner) {
            if(it) {
            //if(it.contentIfNotHandled != null) { - EventWrapper 사용 시.
                loadingDialog.show(childFragmentManager, null)
                context?.let { mContext ->
                    Toast.makeText(mContext, mContext.getString(R.string.edit_complete_msg), Toast.LENGTH_SHORT).show()
                }

                lifecycleScope.launch {
                    delay(3000L)
                    loadingDialog.dismiss()

                    getUserInfo()
                }
            } else {
                context?.let { mContext ->
                    Toast.makeText(mContext, mContext.getString(R.string.please_dont_empty), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getUserInfo() {
        userViewModel.getUserInfo()
        context?.let {
            Toast.makeText(it, it.getString(R.string.get_info_complete_msg), Toast.LENGTH_SHORT).show()
        }
    }

}