package com.khs.aacviewmodelandrecommandedarchitectureexampleproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.repository.MyRepository

class UserInfoViewModelFactory(private val myRepository: MyRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MyRepository::class.java).newInstance(myRepository)
    }
}