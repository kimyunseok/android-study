package com.example.rxjavaexampleproject.repository

import com.example.rxjavaexampleproject.common.MyApplication

class MyRepository {
    private val apiService = MyApplication.apiService

    fun getMyModel() = apiService.getMyModel()

    fun updateMyModel(_idx: Int, _name: String) = apiService.updateMyModel(_idx, _name)
}