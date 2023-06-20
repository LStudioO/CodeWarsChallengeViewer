package com.feature.challenge_details.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class RankDto(
    @Json(name = "color")
    val color: String?,
    @Json(name = "id")
    val rankId: Int?,
    @Json(name = "name")
    val rankName: String?,
)
