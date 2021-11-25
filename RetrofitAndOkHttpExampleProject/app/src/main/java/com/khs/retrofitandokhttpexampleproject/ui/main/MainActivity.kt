package com.khs.retrofitandokhttpexampleproject.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khs.retrofitandokhttpexampleproject.R
import com.khs.retrofitandokhttpexampleproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        showMainFragment()
    }

    private fun showMainFragment() {
        supportFragmentManager.beginTransaction().replace(binding.mainContainer.id, MainFragment()).commit()
    }
}