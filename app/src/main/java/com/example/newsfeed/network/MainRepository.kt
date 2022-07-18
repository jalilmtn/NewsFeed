package com.example.newsfeed.network

class MainRepository constructor(private val retrofitService: RetrofitService) {
    suspend fun getAllNews(page:Int) = retrofitService.getAllNews(page = page)
}