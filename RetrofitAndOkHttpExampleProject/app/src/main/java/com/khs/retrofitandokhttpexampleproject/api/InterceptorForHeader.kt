package com.khs.retrofitandokhttpexampleproject.api

import com.khs.retrofitandokhttpexampleproject.common.GlobalApplication.Companion.prefs
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * 인터셉터는 중간에 매개체가 되어서 처리해준다.
 * 서버에 데이터보낼때 헤더를 자동으로 넣어주는 클래스.
 * SharedPreferences 등에 solvedacToken 등을 저장해서
 * 모든 API에 해당하는 헤더를 일일이 추가하지 않고도 매번 넣어줄 수 있다.
 * */
class InterceptorForHeader: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val solvedacToken = prefs.getString("solvedacToken", "NO_TOKEN")
        if(solvedacToken == "NO_VALUE") {
            builder.addHeader("solvedacToken", "123")
        }
        return chain.proceed(builder.build())
    }
}