package com.example.newsfeed.db

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.newsfeed.network.model.Result

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override fun getNews(): Flow<List<Result>> = (appDatabase.userDao().getAll())

    override fun insertAll(news: List<Result>): Flow<Unit> = flow {
        appDatabase.userDao().insertAll(news)
        emit(Unit)
    }

    override fun delete(news: Result) {
        appDatabase.userDao().delete(news)
    }

    override fun insert(news: Result) {
        appDatabase.userDao().insert(news)
    }

}