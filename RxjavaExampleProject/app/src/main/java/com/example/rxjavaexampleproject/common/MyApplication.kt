package com.example.rxjavaexampleproject.common

import android.app.Application
import com.example.rxjavaexampleproject.model.APIService
import com.example.rxjavaexampleproject.model.MyModel

class MyApplication: Application() {
    companion object {
        lateinit var apiService: APIService
    }

    override fun onCreate() {
        super.onCreate()

        apiService = APIService()
    }
}