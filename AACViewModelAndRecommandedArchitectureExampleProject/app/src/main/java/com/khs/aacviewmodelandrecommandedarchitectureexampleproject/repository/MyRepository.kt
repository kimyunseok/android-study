package com.khs.aacviewmodelandrecommandedarchitectureexampleproject.repository

import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.model.MyModel
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.model.data.MyData

/**
 * Repository를 사용할 경우 Model의 종류에 상관없이
 * ViewModel에 데이터를 넘겨줄 때 일관된 형태로 넘겨줄 수 있다.
 */
class MyRepository {
    private val model = MyModel()

    fun getUserInfo() = model.getUserInfo()
    fun setUserInfo(user: MyData) = model.setUserInfo(user)
}