package com.khs.aacviewmodelandrecommandedarchitectureexampleproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.model.data.MyData
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.repository.MyRepository
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.util.EventWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.NumberFormatException

/**
 * AAC ViewModel Class.
 * UI와 관련된 데이터를 저장하는 클래스이다.
 * Android의 수명주기에 맞게 사용할 수 있는 데이터 클래스이다.
 * 따라서 메모리 누수로부터 자유롭고 화면 회전과 같은 상황에서도 데이터를 보관할 수 있다.
 */
class UserInfoViewModel(private val myRepository: MyRepository): ViewModel() {
    //Binding목적. true PostValue 시, 수정 성공. false PostValue 시, 수정 실패
    //수정 화면에서는 userIdx를 가져올 목적으로 Observe한다. 이유는 userIdx가 Int형인데 양방향 바인딩은 String만 가능하기 때문.
    private val _currentUserInfo = MutableLiveData<EventWrapper<MyData>>()
    val currentUserInfo: LiveData<EventWrapper<MyData>>
        get() = _currentUserInfo

    //Binding목적. 정보 수정하는 곳에서의 유저 정보.
    //양방향 바인딩에서는 수정을 하지 않아도 수정사항이 apply가 돼버리므로 따로 LiveData를 만들어준다.
    val inputUserInfo by lazy { MutableLiveData<MyData>() }

    //Observe목적. true PostValue 시, 수정 성공. false PostValue 시, 수정 실패
    private val _editComplete = MutableLiveData<EventWrapper<Boolean>>()
    val editComplete: LiveData<EventWrapper<Boolean>>
        get() = _editComplete

    //이미 데이터를 가져왔는지 여부를 Check하는 메서드. 수정하지 않는 이상 DB에 한 번만 접근하기 위해서 만든 메서드.
    fun checkAlreadyGetUserInfo(): Boolean {
        // 현재 저장중인 LiveData의 값이 null이라면 가져오지 않았다는 뜻. null이 아니라면 이미 가져왔다는 뜻.
        return currentUserInfo.value?.peekContent() != null
    }

    //User의 정보를 가져온다.
    fun getUserInfo() {
        //CoroutineScope(비동기)로 데이터를 가져온다.
        CoroutineScope(Dispatchers.IO).launch {
            val userData = myRepository.getUserInfo()
            _currentUserInfo.postValue(EventWrapper(userData))
        }
    }

    // Input, 즉 입력하는 유저의 정보를 현재 ViewModel에서 저장중인 데이터로 깊은복사 후 넣어준다.
    fun setInputUserInfo() {
        // 현재 유저 정보 데이터를 깊은 복사한 후에 inputUserInfo에 넣어준다.
        // 깊은 복사를 하지 않을 경우, currentUserInfo의 객체의 변수의 주소들과 같은 변수의 주소들을 참조하게 된다.
        val inputUser = currentUserInfo.value?.peekContent()?.copy()
        inputUserInfo.postValue(inputUser)
    }

    // 유저의 정보를 Update
    fun setUserInfo(_userIdx: String, _userName: String, _userEmail: String) {
        val userIdx: Int

        try {
            userIdx = Integer.valueOf(_userIdx) // UserIdx 입력 포맷 확인.

            if(_userName.isEmpty() || _userEmail.isEmpty()) {
                // 유저이름, 유저이메일 비었는지 확인.
                _editComplete.postValue(EventWrapper(false))
            } else {
                //정상 입력이라면, CoroutineScope(비동기)로 입력받은 데이터 저장.
                CoroutineScope(Dispatchers.IO).launch {
                    val inputData = MyData(userIdx, _userName, _userEmail)
                    myRepository.setUserInfo(inputData)
                    _editComplete.postValue(EventWrapper(true))
                    getUserInfo()
                }
            }

        } catch (e: NumberFormatException) {
            // UserIdx 입력한 값이 이상한 값이라면, 여기에서 실패하게 된다.
            e.printStackTrace()
            _editComplete.postValue(EventWrapper(false))
        } catch (e: Exception) {
            e.printStackTrace()
            _editComplete.postValue(EventWrapper(false))
        }

    }
}