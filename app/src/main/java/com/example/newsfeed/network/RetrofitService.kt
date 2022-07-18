package com.example.newsfeed.network

import com.example.newsfeed.network.model.SearchResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("search")
    suspend fun getAllNews(
        @Query("api-key") apiKey: String = "test",
        @Query("page") page: Int,
        @Query("size") size: Int = 10,
    ): SearchResponse

    companion object {
        var retrofitService: RetrofitService? = null

        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://content.guardianapis.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}