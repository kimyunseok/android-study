package com.khs.aacviewmodelandrecommandedarchitectureexampleproject.ui.fragment

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.R
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.databinding.FragmentUserInfoBinding
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.repository.MyRepository
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.ui.base.BaseFragment
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.util.FragmentTransitionManager
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.viewmodel.UserInfoViewModel
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.viewmodel.UserInfoViewModelFactory

class UserInfoFragment: BaseFragment<FragmentUserInfoBinding>() {
    override var layoutId = R.layout.fragment_user_info

    //만일 Activity 범위 내에서 ViewModel을 초기화하려면 다음과 같이 작성해주면 된다.
    //ViewModelStoreOwner를 Acvitiy로 설정해 주는 것이다.
    override val viewModel: UserInfoViewModel by lazy {
        ViewModelProvider(requireActivity(), UserInfoViewModelFactory(MyRepository())).get(UserInfoViewModel::class.java)
    }

/*   by viewModels를 사용해서 ViewModel을 초기화하면 ViewModel의 생명주기는 Fragment의 생명주기를 따르게 된다. (이는 by lazy 기반이다.)
     by lazy를 사용하면 초기화가 해당 객체가 최초로 사용되기 전까지 미뤄진다.
    override val viewModel: UserInfoViewModel by viewModels {
       UserInfoViewModelFactory(MyRepository())
    }*/

    override fun init() {
        bindViewModel()
        getUserInfo()
        setUpBtnListener()
    }

    //ViewModel 초기화 되는 곳.
    private fun bindViewModel() {
        viewDataBinding.viewModel = viewModel
        Log.d("UserInfoFragment", "ViewModel Address : $viewModel")
    }

    private fun getUserInfo() {
        // 만일 이미 데이터를 가져온 상황이라면 가져오지 않는다.
        if(viewModel.checkAlreadyGetUserInfo().not()) {
            viewModel.getUserInfo()
            Toast.makeText(context, getString(R.string.get_user_info_toast), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpBtnListener() {
        viewDataBinding.editBtn.setOnClickListener {
            FragmentTransitionManager()
                .changeFragmentOnActivity(
                    requireActivity(),
                    R.id.main_container,
                    EditFragment(),
                    true
                )
        }
    }
}