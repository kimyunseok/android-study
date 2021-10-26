package com.khs.webviewexampleproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class WebViewFragment: Fragment() {

    lateinit var webView: MyWebView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initWebView()

        return webView // 나타나는 뷰를 웹뷰로 설정한다.
    }

    //웹뷰 초기화 메서드.
    private fun initWebView() {
        val url = arguments?.getString("url") // 메인 액티비티에서 입력한 주소를 받아온다.
        webView = MyWebView(requireContext(), url.toString())
    }

}