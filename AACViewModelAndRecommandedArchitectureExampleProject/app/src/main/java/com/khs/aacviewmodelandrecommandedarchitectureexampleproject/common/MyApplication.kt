package com.khs.aacviewmodelandrecommandedarchitectureexampleproject.common

import android.app.Application

/**
 * 이 곳에 SharedPreferences를 선언하는 이유는 Context 참조를 위함이다.
 */
class MyApplication: Application() {

    companion object {
        lateinit var mySharedPreferences: MySharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        mySharedPreferences = MySharedPreferences(applicationContext)
    }
}