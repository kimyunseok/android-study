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

        return webView
    }

    private fun initWebView() {
        val url = arguments?.getString("url")
        webView = MyWebView(requireContext(), url.toString())
    }

}