package com.khs.roomdbexampleproject.data.viewmodel.memo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khs.roomdbexampleproject.data.model.Memo
import com.khs.roomdbexampleproject.data.repository.MemoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemoViewModel(private val memoRepository: MemoRepository): ViewModel() {
    val isEdit = MutableLiveData<EditMemoPostData>()
    val isMemoInsertComplete = MutableLiveData<Long>()

    val isMemodeleteComplete = MutableLiveData<Memo>()
    val isMemodeleteByIdComplete = MutableLiveData<Memo>()
    val isMemoModifyComplete = MutableLiveData<EditMemoPostData>()

    val isGetAllMemoComplete = MutableLiveData<List<Memo>>()


    fun changeMode(memo: Memo, _isEdit: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            isEdit.postValue(EditMemoPostData(memo, memo.memo, _isEdit))
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
                isMemodeleteComplete.postValue(memo)
            }
        }
    }

    fun deleteMemoById(memo: Memo) {
        CoroutineScope(Dispatchers.IO).launch {
            memoRepository.deleteMemoByID(memo.id).let {
                isMemodeleteByIdComplete.postValue(memo)
            }
        }
    }

    fun modifyMemo(memo: Memo, editMemo: String) {
        CoroutineScope(Dispatchers.IO).launch {
            memoRepository.modifyMemo(memo.id, editMemo).let {
                isMemoModifyComplete.postValue(EditMemoPostData(memo, editMemo, false))
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

    inner class EditMemoPostData(val memo: Memo, val editMemo: String, val isEdit: Boolean)
}