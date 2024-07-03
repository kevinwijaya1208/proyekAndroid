package com.example.proyekandroid.API

import com.google.gson.annotations.SerializedName

data class ResponseNews(
	@SerializedName("date")
	val date: Long? = null,

	@SerializedName("domain")
	val domain: String? = null,

	@SerializedName("image")
	val image: String? = null,

	@SerializedName("isVideo")
	val isVideo: Boolean? = null,

	@SerializedName("link")
	val link: String? = null,

	@SerializedName("preview")
	val preview: String? = null,

	@SerializedName("pubLogo")
	val pubLogo: String? = null,

	@SerializedName("publisher")
	val publisher: String? = null,

	@SerializedName("relatedArticles")
	val relatedArticles: List<RelatedArticlesItem>? = null,

	@SerializedName("title")
	val title: String? = null
)

data class RelatedArticlesItem(
	@SerializedName("date")
	val date: Long? = null,

	@SerializedName("domain")
	val domain: String? = null,

	@SerializedName("image")
	val image: String? = null,

	@SerializedName("isVideo")
	val isVideo: Boolean? = null,

	@SerializedName("link")
	val link: String? = null,

	@SerializedName("preview")
	val preview: String? = null,

	@SerializedName("pubLogo")
	val pubLogo: String? = null,

	@SerializedName("publisher")
	val publisher: String? = null,

	@SerializedName("title")
	val title: String? = null
)
