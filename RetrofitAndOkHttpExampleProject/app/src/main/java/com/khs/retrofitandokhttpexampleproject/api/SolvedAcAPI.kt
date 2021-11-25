package com.khs.retrofitandokhttpexampleproject.api

import com.khs.retrofitandokhttpexampleproject.model.SolveAcGetUserDataModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SolvedAcAPI {
    @GET("/api/v3/user/show")
    suspend fun getUserData(@Query("handle")handle: String): Response<SolveAcGetUserDataModel>

    @GET("/api/v3/user/show")
    fun getUserDataByCall(@Query("handle")handle: String): Call<SolveAcGetUserDataModel>
}