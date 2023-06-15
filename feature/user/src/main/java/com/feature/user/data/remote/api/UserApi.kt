package com.feature.user.data.remote.api

import com.core.utils.functional.Either
import com.core.utils.platform.network.ApiError
import com.feature.user.data.remote.dto.CompletedChallengesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface UserApi {
    @GET("users/{username}/code-challenges/completed")
    suspend fun getCompletedChallenges(
        @Path("username") username: String,
        @Query("page") page: Int = 0,
    ): Either<ApiError, CompletedChallengesDto>
}
