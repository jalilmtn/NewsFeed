package com.example.newsfeed.db

import kotlinx.coroutines.flow.Flow
import com.example.newsfeed.network.model.Result

interface DatabaseHelper {

    fun getNews(): Flow<List<Result>>

    fun insertAll(news: List<Result>): Flow<Unit>

    fun delete(news: Result)

    fun insert(news: Result)

}