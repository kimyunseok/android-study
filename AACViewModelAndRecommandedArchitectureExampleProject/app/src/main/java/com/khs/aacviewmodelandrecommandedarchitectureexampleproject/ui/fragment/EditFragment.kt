package com.khs.aacviewmodelandrecommandedarchitectureexampleproject.ui.fragment

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.R
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.databinding.FragmentEditBinding
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.repository.MyRepository
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.ui.base.BaseFragment
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.viewmodel.UserInfoViewModel
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.viewmodel.UserInfoViewModelFactory

class EditFragment: BaseFragment<FragmentEditBinding>() {
    override var layoutId = R.layout.fragment_edit
    override val viewModel: UserInfoViewModel by lazy {
        ViewModelProvider(requireActivity(), UserInfoViewModelFactory(MyRepository())).get(UserInfoViewModel::class.java)
    }

    override fun init() {
        bindViewModel()
        initInputData()
        setUpObserver()
    }

    // 만일 입력하고 취소했어도 Update되는 것을 방지하기 위함.
    private fun initInputData() {
        viewModel.setInputUserInfo()
    }

    // ViewModel 초기화 되는 곳. 그러나 MainActivity Scope의 UserInfoViewModel이 이미 존재하므로 해당 ViewModel을 가져오게 된다.
    private fun bindViewModel() {
        viewDataBinding.viewModel = viewModel
        Log.d("EditFragment", "ViewModel Address : $viewModel")
    }

    private fun setUpObserver() {
        //유저 정보 가져온 후에 userIdx를 String으로 변경한 값을 UI에 표시.
        viewModel.currentUserInfo.observe(viewLifecycleOwner) {
            viewDataBinding.userIdx = it.peekContent().userIdx.toString()
        }

        //유저 정보 수정 완료 시, UserInfoFragment로 전환.
        viewModel.editComplete.observe(viewLifecycleOwner) {
            val value = it.contentIfNotHandled // 가장 최근의 값을 기록해둔다. 이제 contentIfNotHandled는 NULL이 된다.

            if(value == true) {
                Toast.makeText(context, getString(R.string.edit_user_info_complete_toast), Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            } else if(value == false){
                Toast.makeText(context, getString(R.string.edit_user_info_fail_toast), Toast.LENGTH_SHORT).show()
            }

        }
    }
}