package com.khs.webviewexampleproject

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        setUpBtnListener(container)

        return binding.root
    }

    private fun setUpBtnListener(container: ViewGroup?) {
        binding.webViewInFragmentBtn.setOnClickListener { startWebViewFragmentBtn(container) }
        binding.webViewInActivityBtn.setOnClickListener { startWebViewActivityBtn() }
        binding.webViewInDialogBtn.setOnClickListener { startWebViewDialogBtn() }
    }

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

    private fun startWebViewActivityBtn() {
        val url = binding.httpsTextView.text.toString() + binding.urlEditText.text.toString()
        val intent = Intent(requireContext(), WebViewActivity::class.java).apply { putExtra("url", url) }
        startActivity(intent)
    }

    private fun startWebViewDialogBtn() {
        val url = binding.httpsTextView.text.toString() + binding.urlEditText.text.toString()
        val mWebView = MyWebView(requireContext(), url)

        val webViewDialog = Dialog(requireContext()).apply {
            window?.attributes?.apply {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.MATCH_PARENT
            }
            setContentView(mWebView)
            setOnKeyListener { _, keyCode, _ ->
                if(keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                    Log.d("check", "hi")
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