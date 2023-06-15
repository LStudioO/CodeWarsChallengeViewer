package com.feature.user.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// CodeWars API is inconsistent, id, name or slug might be null
@JsonClass(generateAdapter = true)
internal data class CompletedChallengeDto(
    @Json(name = "id")
    val id: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "slug")
    val slug: String?,
    @Json(name = "completedLanguages")
    val completedLanguages: List<String>,
    @Json(name = "completedAt")
    val completedAt: String,
)
