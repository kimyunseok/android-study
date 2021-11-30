package com.khs.roomdbexampleproject.common

import android.app.Application

class GlobalApplication: Application() {
    companion object {
        lateinit var appInstance: GlobalApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }
}