package com.khs.roomdbexampleproject.data.repository

import com.khs.roomdbexampleproject.common.GlobalApplication
import com.khs.roomdbexampleproject.data.AppDataBase
import com.khs.roomdbexampleproject.data.model.Memo

class MemoRepository {
    private val appDBInstance = GlobalApplication.appDataBaseInstance.memoDao()

    suspend fun insertMemo(memo: Memo) = appDBInstance.insertMemo(memo)
    suspend fun deleteMemo(memo: Memo) = appDBInstance.deleteMemo(memo)
    suspend fun deleteMemoByID(id: Long) = appDBInstance.deleteMemoByID(id)
    suspend fun getAllMemo() = appDBInstance.getAllMemo()
    suspend fun modifyMemo(id: Long, memo: String) = appDBInstance.modifyMemo(id, memo)
}