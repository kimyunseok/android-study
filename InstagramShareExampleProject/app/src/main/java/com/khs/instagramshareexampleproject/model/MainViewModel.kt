package com.khs.instagramshareexampleproject.model

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khs.instagramshareexampleproject.R

class MainViewModel : ViewModel() {
    //뷰에 나타낼 값들. 라이브데이터 형식
    var image = MutableLiveData<Int>()
    var background = MutableLiveData<Int>()
    val content = MutableLiveData<String>()
    
    //이미지, 배경 리스트들
    private var imageList = mutableListOf<Int>()
    private var backgroundList = mutableListOf<Int>()

    // viewModel에서 onClick메서드 구현
    var randomImageSetClick: View.OnClickListener
    var randomBackgroundSetClick: View.OnClickListener

    init{
        image.value = R.drawable.android

        background.value = R.color.white

        content.value = ""

        imageList = mutableListOf(R.drawable.android,
            R.drawable.camera,
            R.drawable.curry,
            R.drawable.fride_rice,
            R.drawable.glasses,
            R.drawable.ramen,
            R.drawable.smile)

        backgroundList = mutableListOf(
            R.color.white,
            R.color.Ivory,
            R.color.AntiqueWhite,
            R.color.SkyBlue,
            R.color.Azure,
            R.color.Bisque,
            R.color.YellowGreen,
            R.color.PaleGoldenrod,
            R.color.OrangeRed,
            R.color.MediumPurple
        )

        randomBackgroundSetClick = View.OnClickListener {
            val idx = (Math.random() * 10).toInt()
            background.postValue(backgroundList[idx])
        }

        randomImageSetClick = View.OnClickListener {
            val idx = (Math.random() * 7).toInt()
            image.value = (imageList[idx])
        }
    }

}