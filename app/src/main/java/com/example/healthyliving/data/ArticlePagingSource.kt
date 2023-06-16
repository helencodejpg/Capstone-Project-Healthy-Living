package com.example.healthyliving.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.healthyliving.remote.retrofit.ApiService
import com.example.healthyliving.remote.response.ArtikelItem

class ArticlePagingSource(private val apiService: ApiService, token: String) : PagingSource<Int, ArtikelItem>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
    var token: String? = token
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtikelItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getArticle("Bearer $token", position, params.loadSize)

            var data: List<ArtikelItem> = listOf()

            if (!responseData.error) {
                data = responseData.data
            }
            LoadResult.Page(
                data = data,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (data.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArtikelItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}