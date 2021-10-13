package com.khs.doublerecyclerviewusingdatabindingexampleproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.khs.doublerecyclerviewusingdatabindingexampleproject.R
import com.khs.doublerecyclerviewusingdatabindingexampleproject.databinding.ActivityMainBinding
import com.khs.doublerecyclerviewusingdatabindingexampleproject.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(binding.container.id, MainFragment()).commit()
    }
}