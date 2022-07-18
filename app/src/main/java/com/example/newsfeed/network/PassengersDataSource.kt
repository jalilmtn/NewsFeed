package com.example.newsfeed.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsfeed.db.DatabaseHelper
import com.example.newsfeed.network.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class PassengersDataSource(
    private val api: MainRepository,
    private val dbHelper: DatabaseHelper
) :
    PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val nextPageNumber = params.key ?: 1

            val response = api.getAllNews(page = nextPageNumber).response
            val items = response.results.toMutableList()
            val fList = withContext(Dispatchers.IO) { dbHelper.getNews().first() }
            items.forEach {
                it.isFav = it.id == fList.firstOrNull { favItem -> favItem.id == it.id }?.id
            }
            LoadResult.Page(
                data = items,
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < response.pages) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}