package com.khs.designpatternexampleproject.ui.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khs.designpatternexampleproject.model.User
import com.khs.designpatternexampleproject.ui.mvvm.repository.UserRepository

/**
 * AAC(Android Architecture Components) ViewModel 클래스.
 * MVVM 디자인 패턴처럼 사용하기위해 구현된 클래스입니다.
 *
 * 매개변수로 Repository를 받아서 Repository 내에 있는 기능들을 가져다가 사용합니다.
 *
 * inputUserInfo : 입력하는 곳에 양방향 데이터를 하기 위한 유저 LiveData입니다.
 * getUserInfo() 메서드와 연동되며, 유저의 정보가 들어오게 되면
 * copyUserInfoToInputUserInfo() 메서드로 해당 LiveData에 깊은 복사가 이루어진
 * User 객체를 postValue합니다.
 *
 * currentUserInfo : 현재 저장된 유저의 정보를 LiveData 형태로 보관합니다.
 * getUserInfo() 메서드와 연동됩니다.
 * Layout File에서 DataBinding해서 사용합니다.
 * View는 자동으로 이 값이 변하면 이 값에 맞게 UI를 갱신합니다.
 *
 * modifyComplete : 유저 정보 수정이 완료됐는지를 저장해두는 LiveData입니다.
 * modifyUserInfo() 메서드와 연동됩니다.
 * View 소스 코드에서 Observer를 생성해서 사용하게 됩니다.
 * View는 이 값을 Observe하다가 값이 변화되면 UI를 갱신하는 등의 로직을 수행합니다.
 *
 */
class UserViewModel(private val userRepository: UserRepository): ViewModel() {
    private val _inputUserInfo = MutableLiveData<User>() // View에서 Binding된 LiveData
    val inputUserInfo: LiveData<User>
        get() = _inputUserInfo

    private val _currentUserInfo = MutableLiveData<User>() // View에서 Binding된 LiveData.
    val currentUserInfo: LiveData<User>
        get() = _currentUserInfo

    private val _modifyComplete = MutableLiveData<Boolean>() // View에서 Observe를 할 LiveData.
    val modifyComplete: LiveData<Boolean>
        get() = _modifyComplete

    fun getUserInfo() {
        userRepository.getUserInfo().let {
            _currentUserInfo.postValue(it)
            copyUserInfoToInputUserInfo(it)
        }
    }

    fun modifyUserInfo(userEmail: String, userName: String, userContact: String, userAddress: String, userAge: String) {
        if(userEmail.isNotEmpty() && userName.isNotEmpty() && userContact.isNotEmpty() && userAddress.isNotEmpty() && userAge.isNotEmpty()) {
            userRepository.modifyUserInfo(userEmail, userName, userContact, userAddress, userAge).let {
                _modifyComplete.postValue(true)
            }
        } else {
            _modifyComplete.postValue(false)
        }
    }

    private fun copyUserInfoToInputUserInfo(user: User) {
        // 깊은 복사 사용.
        val inputUser = user.copyUser()
        _inputUserInfo.postValue(inputUser)
    }

}