package com.example.proyekandroid.API

import com.google.gson.annotations.SerializedName

//data -> flightquotes -> buckets / results

data class BudgetResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class Content(

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("direct")
	val direct: Boolean? = null,

	@field:SerializedName("outboundLeg")
	val outboundLeg: OutboundLeg? = null,

	@field:SerializedName("rawPrice")
	val rawPrice: Double? = null
)

data class MonthsItem(

	@field:SerializedName("month")
	val month: Int? = null,

	@field:SerializedName("year")
	val year: Int? = null,

	@field:SerializedName("monthLabel")
	val monthLabel: String? = null,

	@field:SerializedName("selected")
	val selected: Boolean? = null
)

data class OutboundLeg(

	@field:SerializedName("originAirport")
	val originAirport: OriginAirport? = null,

	@field:SerializedName("localDepartureDateLabel")
	val localDepartureDateLabel: String? = null,

	@field:SerializedName("destinationAirport")
	val destinationAirport: DestinationAirport? = null,

	@field:SerializedName("localDepartureDate")
	val localDepartureDate: String? = null
)

data class OriginAirport(

	@field:SerializedName("skyCode")
	val skyCode: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class Data(

	@field:SerializedName("flightQuotes")
	val flightQuotes: FlightQuotes? = null,

	@field:SerializedName("differentDestination")
	val differentDestination: DifferentDestination? = null,

	@field:SerializedName("context")
	val context: Context? = null
)

data class DifferentDestination(

	@field:SerializedName("context")
	val context: Context? = null,

	@field:SerializedName("location")
	val location: Location? = null
)

data class ResultsItem(

	@field:SerializedName("entityId")
	val entityId: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("skyId")
	val skyId: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("content")
	val content: Content? = null
)

data class BucketsItem(

	@field:SerializedName("resultIds")
	val resultIds: List<String?>? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("label")
	val label: String? = null
)

data class Context(

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("sessionId")
	val sessionId: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DestinationAirport(

	@field:SerializedName("skyCode")
	val skyCode: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class Location(

	@field:SerializedName("skyCode")
	val skyCode: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class FlightQuotes(

	@field:SerializedName("months")
	val months: List<MonthsItem?>? = null,

	@field:SerializedName("buckets")
	val buckets: List<BucketsItem?>? = null,

	@field:SerializedName("context")
	val context: Context? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null
)
