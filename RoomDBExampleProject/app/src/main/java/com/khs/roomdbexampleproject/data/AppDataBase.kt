package com.khs.roomdbexampleproject.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.khs.roomdbexampleproject.common.GlobalApplication
import com.khs.roomdbexampleproject.data.dao.MemoDao
import com.khs.roomdbexampleproject.data.model.Memo

@Database(entities = [Memo::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun memoDao(): MemoDao
}