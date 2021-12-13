package com.khs.aacviewmodelandrecommandedarchitectureexampleproject.model

import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.common.MyApplication.Companion.mySharedPreferences
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.model.data.MyData

class MyModel {
    fun getUserInfo(): MyData {
        return MyData(
            mySharedPreferences.getInt("userIdx", -1),
            mySharedPreferences.getString("userName", "등록된 이름이 없습니다.").toString(),
            mySharedPreferences.getString("userEmail", "등록된 이메일이 없습니다.").toString(),
        )
    }

    fun setUserInfo(userInfo: MyData) {
        userInfo.let {
            mySharedPreferences.setInt("userIdx", it.userIdx)
            mySharedPreferences.setString("userName", it.userName)
            mySharedPreferences.setString("userEmail", it.userEmail)
        }
    }

}