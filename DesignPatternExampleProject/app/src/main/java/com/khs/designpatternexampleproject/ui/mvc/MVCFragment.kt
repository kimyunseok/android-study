package com.khs.designpatternexampleproject.ui.mvc

import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.khs.designpatternexampleproject.R
import com.khs.designpatternexampleproject.databinding.FragmentUserInfoNoDataBindingBinding
import com.khs.designpatternexampleproject.model.User
import com.khs.designpatternexampleproject.model.UserModel
import com.khs.designpatternexampleproject.ui.base.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * MVC에서는 Activity / Fragment가 View와 Controller의 기능을 모두 수행한다.
 * 따라서 Activity / Fragment에서 모든 기능을 구현할 수 있다.
 * (물론 실제로는 View 계층과 Controller는 분리되어져야 맞다.
 * 그러나 안드로이드에서는 Activity / Fragment가 View / Controller 역할을 하는 것을 MVC 패턴이라고 부른다고 한다.)
 *
 * MVC에서 Controller는 View에는 UI갱신, Model에는 데이터 업데이트 역할을 해줍니다.
 * View는 Model을 직접 참조할 수도있고, Controller를 통해 간접적으로 참조할 수 있다.
 * 그러나 결국에는 Model이 있어야만 하므로 View, Model간에 의존성이 생기게 된다.
 *
 * 장점 :
 * 1. 앱에서 기능을 구현하기가 가장 쉽다.
 * 2. Activity / Fragment에서 모든 동작을 처리해주면 되므로 개발기간이 줄어든다.
 * 3. 기능이 적은 앱이라면, 코드 분석이 용이해진다.
 *
 * 단점 :
 * 1. 하나의 클래스(Activity / Fragment)에 코드의 양이 증가한다. 이는 안드로이드 앱 아키텍처 원칙 '관심사 분리'에 어긋난다.
 * 2. 코드가 나중에 스파게티가 될 가능성이 높으므로 유지 및 보수가 어려워진다.
 * 3. View는 UI 갱신을 위해서 Model을 참조하게 되므로 View와 Model간의 의존성이 높아진다.
 *
 * */

class MVCFragment: BaseFragment<FragmentUserInfoNoDataBindingBinding>() {
    override val layoutId: Int = R.layout.fragment_user_info_no_data_binding

    private val userModel = UserModel()

    override fun init() {
        setMVCTitle()
        setUpBtnListener()

        getUserInfo()
        setInputText()
    }

    private fun setUpBtnListener() {
        binding.editCompleteBtn.setOnClickListener {
            updateUserInfoModel()
        }
    }

    // View -> Controller -> Model : 현재 입력정보를 참조해서 유저 정보 업데이트 (To Server / Local DB)
    private fun updateUserInfoModel() {
        getCurrentInputUserInfo()?.let {
            loadingDialog.show(childFragmentManager, null)

            // 1. View -> Controller -> Model : Controller가 View를 참조해서 DB 정보 Update.
            userModel.modifyUserInfo(
                it.userEmail,
                it.userName,
                it.userContact,
                it.userAddress,
                it.userAge
            )
            setInputText()
            Toast.makeText(requireContext(), context?.getString(R.string.edit_complete_msg), Toast.LENGTH_SHORT).show()

            // 수정된 유저 정보를 불러와 갱신시키는 작업은 불러오는 것을 보여주기 위해 일부러 Delay를 주었다.
            lifecycleScope.launch {
                delay(3000L)
                loadingDialog.dismiss()

                // 2. Model -> View : View가 Model을 참조해서 UI를 Update.
                getUserInfo()
            }
        }
    }

    // Model -> View : View가 Model을 참조해서 UI를 Update. View와 Model간에 의존성이 생기게 된다.
    private fun getUserInfo() {
        binding.userNameTv.text = userModel.getUserInfo().userName
        binding.userAgeTv.text = userModel.getUserInfo().userAge
        binding.userEmailTv.text = userModel.getUserInfo().userEmail
        binding.userContactTv.text = userModel.getUserInfo().userContact
        binding.userAddressTv.text = userModel.getUserInfo().userAddress

        Toast.makeText(requireContext(), context?.getString(R.string.get_info_complete_msg), Toast.LENGTH_SHORT).show()
    }

    // Controller -> View : View EditText Clear. Controller가 View UI를 Update.
    private fun setInputText() {
        binding.inputUserNameEditText.setText(userModel.getUserInfo().userName)
        binding.inputUserAgeEditText.setText(userModel.getUserInfo().userAge)
        binding.inputUserEmailEditText.setText(userModel.getUserInfo().userEmail)
        binding.inputUserContactEditText.setText(userModel.getUserInfo().userContact)
        binding.inputUserAddressEditText.setText(userModel.getUserInfo().userAddress)
    }

    // 현재 입력된 유저의 정보를 토대로 Nullable한 User 객체 Return.
    // 빈 칸이 존재하는 경우 Null Return.
    // View -> Controller : 사용자 이벤트 처리하는 곳.
    private fun getCurrentInputUserInfo(): User? {
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

    // Controller -> View : UI 갱신.
    private fun setMVCTitle() {
        binding.titleTv.text = context?.getString(R.string.start_mvc)
    }
}