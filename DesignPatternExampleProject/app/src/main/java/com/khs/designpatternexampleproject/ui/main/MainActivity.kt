package com.khs.designpatternexampleproject.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khs.designpatternexampleproject.databinding.ActivityMainBinding
import com.khs.designpatternexampleproject.util.FragmentTransitionManager

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setMainFragment()
    }

    private fun setMainFragment() {
        FragmentTransitionManager().changeFragmentOnActivity(this, binding.mainContainer.id, MainFragment(), false)
    }

}