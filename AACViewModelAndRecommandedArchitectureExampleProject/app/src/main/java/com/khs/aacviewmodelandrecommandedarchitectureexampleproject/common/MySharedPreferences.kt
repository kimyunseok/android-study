package com.khs.aacviewmodelandrecommandedarchitectureexampleproject.common

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.R

/**
 * SharedPreferences는 간단한 정보 값을 XML기반의 파일형태로 저장해서 사용하는 것을 의미한다.
 */

class MySharedPreferences(context: Context) {
    /*
     * 첫번째 : XML파일이름
     * 두번째 : 접근 권한
     * MODE_PRIVATE : 이 앱에서만 접근이 가능하다.
     * MODE_WORLD_READABLE : 모든 앱에서 읽기 가능
     * MODE_WORLD_WRITEABLE : 모든 앱에서 쓰기 가능
     * MODE_WORLD_PROCESS : 모든 앱에서 읽기와 쓰기 가능
     */
    private val mySharedPreferences: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    /* GET */
    fun getString(key: String, defaultValue: String?): String? {
        return mySharedPreferences.getString(key, defaultValue)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return mySharedPreferences.getInt(key, defaultValue)
    }

    /* SET
     * commit()은 동기, apply()는 비동기이므로 apply()가 더 빠른 속도로 처리가 가능하다.
     */
    fun setString(key: String, value: String?) {
        mySharedPreferences.edit().putString(key, value).apply()
    }

    fun setInt(key: String, value: Int) {
        mySharedPreferences.edit().putInt(key, value).apply()
    }
}