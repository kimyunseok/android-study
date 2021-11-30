package com.khs.roomdbexampleproject.common

import android.app.Application
import androidx.room.Room
import com.khs.roomdbexampleproject.data.AppDataBase

class GlobalApplication: Application() {
    companion object {
        lateinit var appInstance: GlobalApplication
            private set

        lateinit var appDataBaseInstance: AppDataBase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this

        appDataBaseInstance = Room.databaseBuilder(
            appInstance.applicationContext,
            AppDataBase::class.java, "exampleApp.db"
        )
            .fallbackToDestructiveMigration() // DB version 달라졌을 경우 데이터베이스 초기화
            .allowMainThreadQueries() // 메인 스레드에서 접근 허용
            .build()
    }
}