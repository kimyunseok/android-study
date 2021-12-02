package com.khs.designpatternexampleproject.ui.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khs.designpatternexampleproject.ui.mvvm.repository.UserRepository

class UserViewModelFactory(private val repository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UserRepository::class.java).newInstance(repository)
    }
}