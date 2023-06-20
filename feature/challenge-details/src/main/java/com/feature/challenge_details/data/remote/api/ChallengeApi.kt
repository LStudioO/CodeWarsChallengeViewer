package com.feature.challenge_details.data.remote.api

import com.core.utils.functional.Either
import com.core.utils.platform.network.ApiError
import com.feature.challenge_details.data.remote.dto.ChallengeDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ChallengeApi {
    @GET("code-challenges/{challengeId}")
    suspend fun getDetails(
        @Path("challengeId") id: String,
    ): Either<ApiError, ChallengeDetailsDto>
}
