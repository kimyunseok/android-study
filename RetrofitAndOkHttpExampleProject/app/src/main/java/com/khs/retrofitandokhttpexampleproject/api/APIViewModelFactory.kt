package com.khs.retrofitandokhttpexampleproject.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * AAC ViewModel에서 매개변수로 값을 넘기기 위해서는
 * ViewModelFactory를 사용해야 한다.
 */

class APIViewModelFactory(private val repository: SolvedAcAPIRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SolvedAcAPIRepository::class.java).newInstance(repository)
    }
}