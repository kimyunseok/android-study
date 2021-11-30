package com.khs.roomdbexampleproject.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.khs.roomdbexampleproject.data.model.Memo

@Dao
interface MemoDao {
    @Insert
    fun insertMemo(memo: Memo): Long // Long을 return하면 해당 memo의 id를 알 수 있다.

    @Delete
    fun deleteMemo(memo: Memo)

    @Query("DELETE FROM Memo Where id = :id")
    fun deleteMemoByID(id: Long)

    @Query("SELECT * FROM Memo")
    fun getAllMemo(): List<Memo>

    @Query("UPDATE Memo SET memo = :memo WHERE id = :id")
    fun modifyMemo(id: Long, memo: String)
}