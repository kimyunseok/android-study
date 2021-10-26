package com.khs.webviewexampleproject

import android.annotation.SuppressLint
import android.content.Context
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

@SuppressLint("SetJavaScriptEnabled")
class MyWebView(context: Context, url: String): WebView(context) {

    init {
        settings.apply {
            javaScriptEnabled = true //자바스크립트 허용.

            setSupportMultipleWindows(true) //웹뷰에서 새 창을 열 수 있도록 허용하는 메서드.
        }

        webViewClient = object: WebViewClient() { // 브라우저를 사용하지 않고, 웹뷰에서 URL을 열도록 한다.
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }
        }

        if (url != null) {
            loadUrl(url)
        }
    }
}