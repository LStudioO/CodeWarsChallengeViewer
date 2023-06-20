package com.feature.challenge_details.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class UnresolvedDto(
    @Json(name = "issues")
    val issues: Int?,
    @Json(name = "suggestions")
    val suggestions: Int?,
)
