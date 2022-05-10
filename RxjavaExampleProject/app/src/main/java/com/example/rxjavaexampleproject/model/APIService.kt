package com.example.rxjavaexampleproject.model

class APIService {

    private val myModel = MyModel(0, "NAME")

    fun getMyModel(): MyModel {
        return myModel
    }

    fun updateMyModel(_idx: Int, _name: String) {
        myModel.apply {
            idx = _idx
            name = _name
        }
    }

}