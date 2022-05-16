package com.example.paging3exampleproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paging3exampleproject.repo.MyRepository

class MainViewModelFactory(private val myRepository: MyRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MyRepository::class.java).newInstance(myRepository)
    }
}