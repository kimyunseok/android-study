package com.khs.roomdbexampleproject.data.repository

import com.khs.roomdbexampleproject.common.GlobalApplication
import com.khs.roomdbexampleproject.data.AppDataBase
import com.khs.roomdbexampleproject.data.model.Memo

class MemoRepository {
    private val appDBInstance = GlobalApplication.appDataBaseInstance.memoDao()

    fun insertMemo(memo: Memo) = appDBInstance.insertMemo(memo)
    fun deleteMemo(memo: Memo) = appDBInstance.deleteMemo(memo)
    fun deleteMemoByID(id: Long) = appDBInstance.deleteMemoByID(id)
    fun getAllMemo() = appDBInstance.getAllMemo()
    fun modifyMemo(id: Long, memo: String) = appDBInstance.modifyMemo(id, memo)
}