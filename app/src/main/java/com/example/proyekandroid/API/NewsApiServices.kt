package com.example.proyekandroid.API

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApiServices {

    @Headers(
        "x-rapidapi-key: a2d907d953msh7615bb79bc43087p128fdcjsnee7db7d85ff9",
        "x-rapidapi-host: newsapi90.p.rapidapi.com"
    )
    @GET("search")
    fun getNews(
        @Query("query") query:String,
        @Query("language") language:String,
        @Query("region") region:String,
    ) : Call<List<ResponseNews>>
}