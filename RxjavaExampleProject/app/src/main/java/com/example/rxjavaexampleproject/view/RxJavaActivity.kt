package com.example.rxjavaexampleproject.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rxjavaexampleproject.databinding.ActivityUserInfoBinding
import com.example.rxjavaexampleproject.model.MyModel
import com.example.rxjavaexampleproject.repository.MyRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class RxJavaActivity: AppCompatActivity() {
    lateinit var viewBinding: ActivityUserInfoBinding

    private var currentIdx: Int = 0
    private val myRepository: MyRepository by lazy { MyRepository() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityUserInfoBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        init()

        setUpBtnListener()
    }

    private fun getObservable() {
        Observable
            .just(myRepository.getMyModel())
            //.subscribeOn(Schedulers.io()) 네트워크 작업일 경우 IO 스레드에서 작업.
            .subscribe (
                object: DisposableObserver<MyModel>() {
                    override fun onNext(t: MyModel) {
                        currentIdx = t.idx
                        viewBinding.userIdxTv.text = t.idx.toString()
                        viewBinding.userNameTv.text = t.name
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(baseContext, "에러가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                    }

                    override fun onComplete() {
                        // onComplete() 호출시 자동으로 dispose() 메서드 호출됨.
                        Log.d("RX::", "유저 정보 가져오기 성공.")
                    }

                }
            )
    }

    private fun init() {
        getObservable()
    }

    private fun setUpBtnListener() {
        viewBinding.inputCompleteBtn.setOnClickListener {
            val newName = viewBinding.userNameEditText.text.toString()
            myRepository.updateMyModel(currentIdx + 1, newName)

            getObservable()
        }
    }
}