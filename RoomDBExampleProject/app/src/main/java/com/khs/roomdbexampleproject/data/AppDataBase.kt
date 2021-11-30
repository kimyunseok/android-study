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

    companion object {
        val appDataBaseInstance = Room.databaseBuilder(
            GlobalApplication.appInstance,
            AppDataBase::class.java, "exampleApp.db"
        )
            .fallbackToDestructiveMigration() // DB version 달라졌을 경우 데이터베이스 초기화
            .allowMainThreadQueries() // 메인 스레드에서 접근 허용
            .build()
    }
}