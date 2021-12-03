package com.khs.designpatternexampleproject.ui.mvvm.repository

import com.khs.designpatternexampleproject.model.UserModel

/**
 * Repository를 쓰면 데이터의 출처에 상관없이
 * 상위 계층에서 일관성있는 데이터의 처리가 가능해진다.
 */
class UserRepository {
    val userModel = UserModel()

    fun getUserInfo() = userModel.getUserInfo()
    fun modifyUserInfo(userEmail: String, userName: String, userContact: String, userAddress: String, userAge: String) = userModel.modifyUserInfo(userEmail, userName, userContact, userAddress, userAge)
}