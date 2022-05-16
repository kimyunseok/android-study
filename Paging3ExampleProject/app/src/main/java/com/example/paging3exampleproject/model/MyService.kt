package com.example.paging3exampleproject.model

import com.example.paging3exampleproject.data.MyModel

interface MyService {
    suspend fun getMyModel(pageId: Long): List<MyModel>
}