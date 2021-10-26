package com.khs.webviewexampleproject

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.khs.webviewexampleproject.databinding.FragmentMainBinding

class MainFragment: Fragment() {

    lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        setUpBtnListener(container) // 프래그먼트가 담긴 container ViewGroup을 매개변수로 넘겨주고, 버튼 리스너 설정.

        return binding.root
    }

    // 버튼 리스너 설정 메서드.
    private fun setUpBtnListener(container: ViewGroup?) {
        binding.webViewInFragmentBtn.setOnClickListener { startWebViewFragmentBtn(container) }
        binding.webViewInActivityBtn.setOnClickListener { startWebViewActivityBtn() }
        binding.webViewInDialogBtn.setOnClickListener { startWebViewDialogBtn() }
    }

    // 프래그먼트 교체 후 웹뷰를 보여주게 하는 메서드.
    private fun startWebViewFragmentBtn(container: ViewGroup?) {
        val url = binding.httpsTextView.text.toString() + binding.urlEditText.text.toString()

        val bundle = Bundle().apply { putString("url", url) }

        val mWebViewFragment = WebViewFragment().apply { arguments = bundle }
        container?.id?.let {
                containerID ->
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(containerID, mWebViewFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    // 새로운 액티비티를 띄워서 그 액티비티에 웹뷰를 보여주게 하는 메서드.
    private fun startWebViewActivityBtn() {
        val url = binding.httpsTextView.text.toString() + binding.urlEditText.text.toString()
        val intent = Intent(requireContext(), WebViewActivity::class.java).apply { putExtra("url", url) }
        startActivity(intent)
    }

    // 다이얼로그 창을 띄워서 웹뷰를 보여주게 하는 메서드.
    private fun startWebViewDialogBtn() {
        val url = binding.httpsTextView.text.toString() + binding.urlEditText.text.toString()
        val mWebView = MyWebView(requireContext(), url)

        val webViewDialog = Dialog(requireContext()).apply {

            window?.attributes?.apply {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.MATCH_PARENT
            }
            setContentView(mWebView)

            //뒤로가기 설정. 만일 webView의 history가 쌓여있다면 webView 뒤로가기. 아니라면 dismiss()되도록 했다.
            setOnKeyListener { _, keyCode, _ ->
                if(keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                    mWebView.goBack()
                    true
                } else {
                    false
                }
            }
        }

        webViewDialog.show()
    }
}