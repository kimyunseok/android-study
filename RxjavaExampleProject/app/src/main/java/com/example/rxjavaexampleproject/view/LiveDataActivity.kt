package com.example.rxjavaexampleproject.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.rxjavaexampleproject.databinding.ActivityUserInfoBinding
import com.example.rxjavaexampleproject.repository.MyRepository
import com.example.rxjavaexampleproject.viewmodel.MyViewModel
import com.example.rxjavaexampleproject.viewmodel.MyViewModelFactory

/**
 * LiveData를 통해 UI를 업데이트하는 Activity입니다.
 * DataBinding이 아닌, Observer 패턴을 통해 UI를 Update했습니다.
 */
class LiveDataActivity: AppCompatActivity() {
    lateinit var viewBinding: ActivityUserInfoBinding

    private val myViewModel: MyViewModel by lazy {
        ViewModelProvider(this, MyViewModelFactory(MyRepository())).get(MyViewModel::class.java)
    }

    private fun init() {
        myViewModel.getMyModelInfo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityUserInfoBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        setUpObserver()
        setUpBtnListener()

        init()
    }

    //LiveData를 통해서 Observer 패턴을 이용해 View를 업데이트합니다.
    private fun setUpObserver() {
        myViewModel.myModelLiveData.observe(this) {
            viewBinding.userIdxTv.text = it.idx.toString()
            viewBinding.userNameTv.text = it.name
        }
    }

    private fun setUpBtnListener() {
        viewBinding.inputCompleteBtn.setOnClickListener {
            val newName = viewBinding.userNameEditText.text.toString()
            myViewModel.editMyModel(newName)

            myViewModel.getMyModelInfo()
        }
    }
}