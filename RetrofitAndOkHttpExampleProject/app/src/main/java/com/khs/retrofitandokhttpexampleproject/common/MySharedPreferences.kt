package com.khs.retrofitandokhttpexampleproject.common

import android.content.Context
import android.content.SharedPreferences
import com.khs.retrofitandokhttpexampleproject.R

/**
 * 저장하는 데이터가 작은 경우, SharedPreferences를 사용한다.
 * https://developer.android.com/training/data-storage/shared-preferences?hl=ko
 */
class MySharedPreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.prefs_key), Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String?): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }
}