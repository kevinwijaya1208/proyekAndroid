package com.example.proyekandroid.API

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiServices {
    
    @Headers(
        "x-rapidapi-key: 7aeede1948msh0892a3bc2b0eddcp11c82djsn22a32bc4d591",
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
}