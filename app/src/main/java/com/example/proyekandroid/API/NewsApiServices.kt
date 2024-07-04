package com.example.proyekandroid.API

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApiServices {

    @Headers(
        "x-rapidapi-key: a0425a3098mshadc60024d6d2527p141081jsnb15c636173d1",
        "x-rapidapi-host: newsapi90.p.rapidapi.com"
    )
    @GET("search")
    fun getNews(
        @Query("query") query:String,
        @Query("language") language:String,
        @Query("region") region:String,
    ) : Call<List<ResponseNews>>
}