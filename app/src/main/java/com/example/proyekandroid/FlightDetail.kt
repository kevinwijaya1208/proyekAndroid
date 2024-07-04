package com.example.proyekandroid

data class FlightDetail(
    var price: Int,
    var direct: Boolean,
    var departureDate : String,
    var skycode : ArrayList<String>,
    var type1: ArrayList<String>
)
