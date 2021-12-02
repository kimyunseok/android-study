package com.khs.designpatternexampleproject.model

/**
 * Design Pattern에서 Model이란, Data를 포함해서 Data를 송, 수신하는 모든 행위를 말한다.
 * 여기서 UserModel 클래스는 Room DB (SQLite) / Retrofit2 등
 * Local / Server DB 통신을 하는 매개체이다.
 */
class UserModel {
    private val userInfo = User("NO_EMAIL", "NO_NAME", "NO_CONTACT", "NO_ADDRESS", "-1")

    fun getUserInfo(): User {
        return userInfo
    }

    fun modifyUserInfo(_userEmail: String, _userName: String, _userContact: String, _userAddress: String, _userAge: String) {
        userInfo.apply {
            userEmail = _userEmail
            userName = _userName
            userContact = _userContact
            userAddress = _userAddress
            userAge = _userAge
        }
    }
}