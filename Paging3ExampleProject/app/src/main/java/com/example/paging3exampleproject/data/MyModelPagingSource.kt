package com.example.paging3exampleproject.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paging3exampleproject.model.MyService
import kotlinx.coroutines.delay
import java.lang.Exception

class MyModelPagingSource(private val myService: MyService, private val pageSize: Int): PagingSource<Long, MyModel>() {
    override fun getRefreshKey(state: PagingState<Long, MyModel>): Long? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, MyModel> {
        return try {
            delay(1000) // 1초의 딜레이

            // 서버와 연동 없이 사용할때
            val pageId = params.key?: 1
            val response = myService.getMyModel(pageId)
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = pageId + 1
            )

            /* 서버와 연동할 때, */
//            val pageId = params.key?: 1
//            val response = myService.getMyModel(pageId)
//            val myModelList = response.body()?: listOf()
//
//            if(response.isSuccessful && myModelList.isNotEmpty()) {
//                LoadResult.Page(
//                    data = myModelList,
//                    prevKey = null,
//                    nextKey = pageId + 1
//                )
//            } else {
//                LoadResult.Page(
//                    data = myModelList,
//                    prevKey = null,
//                    nextKey = null
//                )
//            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}