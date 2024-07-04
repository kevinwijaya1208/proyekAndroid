package com.example.proyekandroid.API

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiServices {
    
    @Headers(
        "x-rapidapi-key: b63323b980msh16f7cc00383c503p1d5d18jsn5aa4b1f9c207",
        "x-rapidapi-host: sky-scanner3.p.rapidapi.com"
    )

    @GET("auto-complete")
    fun getLocationId(
        @Query ("query") query:String,
        @Query ("placeTypes") placeTypes:String,
        @Query ("outboundDate") outboundDate:String,
        @Query ("market") market:String,
        @Query ("locale") locale:String
    ) : Call<ResponseFlightID>

    @GET("search-everywhere")
    fun getFlightDetail(
        @Query ("fromEntityId") fromEntityID: String,
        @Query ("toEntityId") toEntityID: String,
        @Query ("type") type: String
    ) : Call<BudgetResponse>
}