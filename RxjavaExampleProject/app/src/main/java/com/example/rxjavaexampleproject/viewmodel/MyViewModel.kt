package com.example.rxjavaexampleproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rxjavaexampleproject.model.MyModel
import com.example.rxjavaexampleproject.repository.MyRepository

class MyViewModel(private val myRepository: MyRepository): ViewModel() {
    private val _myModelLiveData = MutableLiveData<MyModel>()
    val myModelLiveData: LiveData<MyModel>
        get() = _myModelLiveData
    private val myModel: MyModel?
        get() = myModelLiveData.value

    fun getMyModelInfo() {
        val model = myRepository.getMyModel()
        _myModelLiveData.postValue(model)
    }

    fun editMyModel(_name: String) = myModel?.idx?.let { _idx -> myRepository.updateMyModel(_idx + 1 , _name) }
}