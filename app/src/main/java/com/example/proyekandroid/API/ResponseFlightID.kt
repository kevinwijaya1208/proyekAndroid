package com.example.proyekandroid.API

import com.google.gson.annotations.SerializedName

data class ResponseFlightID(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataItem(

	@field:SerializedName("presentation")
	val presentation: Presentation? = null,

	@field:SerializedName("navigation")
	val navigation: Navigation? = null
)

data class RelevantHotelParams(

	@field:SerializedName("localizedName")
	val localizedName: String? = null,

	@field:SerializedName("entityType")
	val entityType: String? = null,

	@field:SerializedName("entityId")
	val entityId: String? = null
)

data class RelevantFlightParams(

	@field:SerializedName("localizedName")
	val localizedName: String? = null,

	@field:SerializedName("flightPlaceType")
	val flightPlaceType: String? = null,

	@field:SerializedName("entityId")
	val entityId: String? = null,

	@field:SerializedName("skyId")
	val skyId: String? = null
)

data class Presentation(

	@field:SerializedName("subtitle")
	val subtitle: String? = null,

	@field:SerializedName("suggestionTitle")
	val suggestionTitle: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("skyId")
	val skyId: String? = null
)

data class Navigation(

	@field:SerializedName("localizedName")
	val localizedName: String? = null,

	@field:SerializedName("entityType")
	val entityType: String? = null,

	@field:SerializedName("entityId")
	val entityId: String? = null,

	@field:SerializedName("relevantFlightParams")
	val relevantFlightParams: RelevantFlightParams? = null,

	@field:SerializedName("relevantHotelParams")
	val relevantHotelParams: RelevantHotelParams? = null
)
