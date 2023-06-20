package com.feature.challenge_details.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ActionByDto(
    @Json(name = "url")
    val url: String,
    @Json(name = "username")
    val username: String,
)
