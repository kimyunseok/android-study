package com.khs.viewbindingdatabindingexample.model

import android.view.View
import androidx.lifecycle.MutableLiveData

data class ViewModel (val image: Int, var content: MutableLiveData<String>, var showImage: MutableLiveData<Boolean>) {
    fun onClick(view: View) {
        showImage.postValue(showImage.value?.not())
    }
}