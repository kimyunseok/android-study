package com.khs.retrofitandokhttpexampleproject.api

import com.khs.retrofitandokhttpexampleproject.common.GlobalApplication

/**
 * 앱 아키텍처 가이드에 따른 Repository.
 */
class SolvedAcAPIRepository {
    private val solvedAcClient = GlobalApplication.baseService.create(SolvedAcAPI::class.java)

    suspend fun getUserData(handle: String) = solvedAcClient.getUserData(handle)
}