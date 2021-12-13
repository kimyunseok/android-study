package com.khs.aacviewmodelandrecommandedarchitectureexampleproject.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.R
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.databinding.ActivityMainBinding
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.ui.fragment.UserInfoFragment
import com.khs.aacviewmodelandrecommandedarchitectureexampleproject.util.FragmentTransitionManager

class MainActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setUpUserInfoFragment()
    }

    private fun setUpUserInfoFragment() {
        FragmentTransitionManager()
            .changeFragmentOnActivity(
                this,
                viewBinding.mainContainer.id,
                UserInfoFragment(),
                false)
    }
}