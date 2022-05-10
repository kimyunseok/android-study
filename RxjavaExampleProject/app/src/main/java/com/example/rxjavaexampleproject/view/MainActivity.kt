package com.example.rxjavaexampleproject.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.rxjavaexampleproject.databinding.ActivityMainBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.observers.DisposableObserver

class MainActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        setUpBtnListener()
    }

    private fun setUpBtnListener() {
        viewBinding.liveDataBtn.setOnClickListener {
            val intent = Intent(baseContext, LiveDataActivity::class.java)
            startActivity(intent)
        }

        viewBinding.rxBtn.setOnClickListener {
            val intent = Intent(baseContext, RxJavaActivity::class.java)
            startActivity(intent)
        }
    }
}