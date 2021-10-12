package com.khs.viewbindingdatabindingexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khs.viewbindingdatabindingexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment()
    }

    private fun setFragment() {
        supportFragmentManager.beginTransaction().replace(binding.container.id, MainFragment()).commit()
    }
}