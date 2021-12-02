package com.khs.designpatternexampleproject.ui.mvvm.repository

import com.khs.designpatternexampleproject.model.UserModel

class UserRepository {
    val userModel = UserModel()

    fun getUserInfo() = userModel.getUserInfo()
    fun modifyUserInfo(userEmail: String, userName: String, userContact: String, userAddress: String, userAge: String) = userModel.modifyUserInfo(userEmail, userName, userContact, userAddress, userAge)
}