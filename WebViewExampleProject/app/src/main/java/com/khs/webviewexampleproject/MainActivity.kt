package com.khs.webviewexampleproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import com.khs.webviewexampleproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpFragment()
    }

    private fun setUpFragment() {
        supportFragmentManager.beginTransaction().replace(binding.container.id, MainFragment()).commit()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val currentFragment = supportFragmentManager.findFragmentById(binding.container.id)
        //현재 컨테이너에 보이는 프래그먼트 보여주기
        
        if(currentFragment is WebViewFragment) { // 웹뷰 프래그먼트 웹뷰 뒤로가기 history 적용.
            if(currentFragment.webView.canGoBack()) {
                currentFragment.webView.goBack()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}