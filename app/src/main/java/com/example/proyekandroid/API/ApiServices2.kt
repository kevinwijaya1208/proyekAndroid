package com.example.proyekandroid.API

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiServices2 {
    @Headers(
        "x-rapidapi-key: a0425a3098mshadc60024d6d2527p141081jsnb15c636173d1",
        "x-rapidapi-host: sky-scanner3.p.rapidapi.com"
    )

    @GET("search-everywhere")
    fun getFlightDetail(
        @Query("fromEntityId") fromEntityID: String,
        @Query("toEntityId") toEntityID: String,
        @Query("type") type: String
    ) : Call<BudgetResponse>
}