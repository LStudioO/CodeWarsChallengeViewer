package com.feature.user.data.remote.api

import com.core.utils.functional.Either
import com.core.utils.platform.network.ApiError
import com.core.utils.platform.network.UnknownApiError
import com.feature.user.data.remote.dto.CompletedChallengeDto
import com.feature.user.data.remote.dto.CompletedChallengesDto

internal const val API_PAGE_COUNT = 2
internal const val TEST_USER = "TestUser"

internal class FakeUserApi : UserApi {

    private val model = mutableMapOf<String, MutableList<CompletedChallengeDto>>()

    fun addCompletedChallenge(
        username: String,
        item: CompletedChallengeDto,
    ) {
        if (model.containsKey(username)) {
            model[username]?.add(item)
        } else {
            model[username] = mutableListOf(item)
        }
    }

    override suspend fun getCompletedChallenges(
        username: String,
        page: Int,
    ): Either<ApiError, CompletedChallengesDto> {
        val list = model[username] ?: return Either.Left(UnknownApiError(Exception()))
        val startIndex = page * API_PAGE_COUNT
        val endIndex = minOf(list.size, startIndex + API_PAGE_COUNT)
        val data = if (startIndex > endIndex) emptyList() else list.subList(startIndex, endIndex)
        return Either.Right(
            CompletedChallengesDto(
                data = data,
                totalItems = list.size,
                totalPages = (list.size / API_PAGE_COUNT) + 1,
            ),
        )
    }
}
