package com.example.coroutineexampleproject

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

class MainActivity : AppCompatActivity() {

    private val imageURL = "https://picsum.photos/1024/1024"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    suspend fun getImage(): Bitmap? = suspendCancellableCoroutine {
        val urlConnection: URLConnection = URL(imageURL).openConnection()
    }
}