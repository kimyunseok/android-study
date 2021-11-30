package com.khs.roomdbexampleproject.data.viewmodel.memo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khs.roomdbexampleproject.data.model.Memo
import com.khs.roomdbexampleproject.data.repository.MemoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemoViewModel(private val memoRepository: MemoRepository): ViewModel() {
    val isEdit = MutableLiveData<Boolean>()
    val isMemoInsertComplete = MutableLiveData<Long>()

    val isMemodeleteComplete = MutableLiveData<Long>()
    val isMemodeleteByIdComplete = MutableLiveData<Long>()
    val isMemoModifyComplete = MutableLiveData<Long>()

    val isGetAllMemoComplete = MutableLiveData<List<Memo>>()

    // System.currentTimeMillis() 값을 넣어줌으로써, observer가 무조건 반응할 수 있도록 만들었다.
    fun changeMode(_isEdit: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            isEdit.postValue(_isEdit)
        }
    }

    fun insertMemo(memo: Memo) {
        CoroutineScope(Dispatchers.IO).launch {
            memoRepository.insertMemo(memo).let {
                id ->
                isMemoInsertComplete.postValue(id)
            }
        }
    }

    fun deleteMemo(memo: Memo) {
        CoroutineScope(Dispatchers.IO).launch {
            memoRepository.deleteMemo(memo).let {
                isMemodeleteComplete.postValue(System.currentTimeMillis())
            }
        }
    }

    fun deleteMemoById(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            memoRepository.deleteMemoByID(id).let {
                isMemodeleteByIdComplete.postValue(System.currentTimeMillis())
            }
        }
    }

    fun modifyMemo(id: Long, memo: String) {
        CoroutineScope(Dispatchers.IO).launch {
            memoRepository.modifyMemo(id, memo).let {
                isMemoModifyComplete.postValue(System.currentTimeMillis())
            }
        }
    }

    fun getAllMemo() {
        CoroutineScope(Dispatchers.IO).launch {
            memoRepository.getAllMemo().let {
                isGetAllMemoComplete.postValue(it)
            }
        }
    }
}