package com.khs.designpatternexampleproject.ui.mvp

import android.content.Context
import android.widget.Toast
import com.khs.designpatternexampleproject.R
import com.khs.designpatternexampleproject.databinding.FragmentUserInfoNoDataBindingBinding
import com.khs.designpatternexampleproject.model.User
import com.khs.designpatternexampleproject.model.UserModel
import com.khs.designpatternexampleproject.ui.base.BaseFragment
import com.khs.designpatternexampleproject.ui.mvp.contract.MVPContract
import com.khs.designpatternexampleproject.ui.mvp.presenter.MVPPresenter

/**
 * MVC 패턴에서의 문제점은 View와 Model간의 의존성이 존재한다는 점이다.
 * View와 Model 간에 의존성이 존재할 경우 앱 테스트를 어렵게 만든다.
 * 또한 Controller에서 View, Model 관련 로직을 처리하므로
 * 나중에 유지, 보수가 어려워지고 가독성도 떨어지게 된다.
 *
 * 이러한 문제를 해결하기 위해 나온게 MVP 패턴이다. MVP 패턴에서의 P는 Presenter이다.
 * Presenter에서는 Model에서 데이터를 가져오고 View에서 UI를 갱신한다.
 * View와 Model간에 의존성을 없앤 디자인 패턴이다.
 *
 * 장점 :
 * 1. View - Presenter, Presenter - Model 간은 인터페이스(Contract)를 통해 이루어진다.
 * 2. Presenter와 View가 1 : 1 관계이다.
 * 3. View와 Model은 서로에 대한 참조가 없다. 즉, 의존성이 없다.
 *
 * 단점 :
 * 1. 1 : 1 관계이므로 View와 Presenter 사이에 의존성이 강해진다.
 * 2. 1 : 1 관계이므로 View에서 처리해야할 기능이 많아지면 그만큼 Presenter의 코드도 길어진다.
 *
 */
class MVPFragment: BaseFragment<FragmentUserInfoNoDataBindingBinding>(), MVPContract.View {
    override val layoutId: Int = R.layout.fragment_user_info_no_data_binding

    private val mvpPresenter = MVPPresenter(UserModel(), this)

    override fun init() {
        setMVPTitle()
        setUpBtnListener()

        mvpPresenter.initUserInfo()
    }

    override val mContext: Context?
        get() = context

    override fun setMVPTitle() {
        binding.titleTv.text = context?.getString(R.string.start_mvp)
    }

    override fun setUpBtnListener() {
        binding.editCompleteBtn.setOnClickListener {
            mvpPresenter.updateUserInfoModel()
        }
    }

    override fun setInputText(user: User?) {
        user?.let {
            binding.inputUserNameEditText.setText(it.userName)
            binding.inputUserAgeEditText.setText(it.userAge)
            binding.inputUserEmailEditText.setText(it.userEmail)
            binding.inputUserContactEditText.setText(it.userContact)
            binding.inputUserAddressEditText.setText(it.userAddress)
        }
    }

    override fun getUserInfo(user: User?) {
        user?.let {
            binding.userNameTv.text = it.userName
            binding.userAgeTv.text = it.userAge
            binding.userEmailTv.text = it.userEmail
            binding.userContactTv.text = it.userContact
            binding.userAddressTv.text = it.userAddress

            Toast.makeText(requireContext(), context?.getString(R.string.get_info_complete_msg), Toast.LENGTH_SHORT).show()
        }
    }

    override fun getCurrentInputUserInfo(): User? {
        val userName = binding.inputUserNameEditText.text.toString()
        val userAge = binding.inputUserAgeEditText.text.toString()
        val userEmail = binding.inputUserEmailEditText.text.toString()
        val userContact = binding.inputUserContactEditText.text.toString()
        val userAddress = binding.inputUserAddressEditText.text.toString()

        if(userName.trim().isEmpty().not() &&
            userAge.trim().isEmpty().not() &&
            userEmail.trim().isEmpty().not() &&
            userContact.trim().isEmpty().not() &&
            userAddress.trim().isEmpty().not()) {
            return User(userEmail, userName, userContact, userAddress, userAge)
        } else {
            Toast.makeText(requireContext(), context?.getString(R.string.please_dont_empty), Toast.LENGTH_SHORT).show()
        }

        return null
    }

    override fun showLoadingDialog() {
        loadingDialog.show(childFragmentManager, null)
    }

    override fun hideLoadingDialog() {
        loadingDialog.dismiss()
    }

    override fun showToastMessage(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}