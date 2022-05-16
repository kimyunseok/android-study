package com.example.paging3exampleproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.paging3exampleproject.repo.MyRepository

class MainViewModel(private val myRepository: MyRepository): ViewModel() {
    val myModelPagingLiveData = myRepository.getMyModelList().asLiveData()
}