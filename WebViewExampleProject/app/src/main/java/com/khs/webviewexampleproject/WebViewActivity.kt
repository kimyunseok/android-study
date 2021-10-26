package com.khs.webviewexampleproject

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity: AppCompatActivity() {

    lateinit var webView: MyWebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initWebView()

        setContentView(webView)
    }

    //웹뷰 초기화 메서드.
    private fun initWebView() {
        val url = intent.getStringExtra("url")
        webView = MyWebView(baseContext, url.toString())
    }

    //뒤로가기 버튼 로직 설정.
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            // 입력한 키값이 뒤로가기 버튼이고 웹뷰의 history가 쌓여있다면 웹뷰에서 뒤로가기 처리를 한다.
            webView.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event) // 아니라면 원래 키 이벤트 처리를 한다.
    }
}