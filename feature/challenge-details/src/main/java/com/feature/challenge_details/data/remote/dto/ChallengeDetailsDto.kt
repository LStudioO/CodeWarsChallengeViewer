package com.feature.challenge_details.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ChallengeDetailsDto(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String?,
    @Json(name = "slug")
    val slug: String?,
    @Json(name = "category")
    val category: String?,
    @Json(name = "publishedAt")
    val publishedAt: String?,
    @Json(name = "approvedAt")
    val approvedAt: String?,
    @Json(name = "languages")
    val languages: List<String>?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "rank")
    val rank: RankDto?,
    @Json(name = "createdAt")
    val createdAt: String?,
    @Json(name = "createdBy")
    val createdBy: ActionByDto?,
    @Json(name = "approvedBy")
    val approvedBy: ActionByDto?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "totalAttempts")
    val totalAttempts: Int?,
    @Json(name = "totalCompleted")
    val totalCompleted: Int?,
    @Json(name = "totalStars")
    val totalStars: Int?,
    @Json(name = "voteScore")
    val voteScore: Int?,
    @Json(name = "tags")
    val tags: List<String>?,
    @Json(name = "contributorsWanted")
    val contributorsWanted: Boolean?,
    @Json(name = "unresolved")
    val unresolved: UnresolvedDto?,
)
