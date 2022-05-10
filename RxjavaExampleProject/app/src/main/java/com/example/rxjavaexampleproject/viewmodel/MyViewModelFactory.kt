package com.example.rxjavaexampleproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rxjavaexampleproject.repository.MyRepository

class MyViewModelFactory(private val myRepository: MyRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MyRepository::class.java).newInstance(myRepository)
    }
}