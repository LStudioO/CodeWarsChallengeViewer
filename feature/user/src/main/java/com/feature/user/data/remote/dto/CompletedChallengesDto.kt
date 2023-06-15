package com.feature.user.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class CompletedChallengesDto(
    @Json(name = "data")
    val data: List<CompletedChallengeDto>,
    @Json(name = "totalItems")
    val totalItems: Int,
    @Json(name = "totalPages")
    val totalPages: Int,
)
