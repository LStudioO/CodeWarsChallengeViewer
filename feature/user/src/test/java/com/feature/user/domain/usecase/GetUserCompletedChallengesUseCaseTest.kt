package com.feature.user.domain.usecase

import androidx.paging.PagingData
import com.feature.user.domain.api.UserRepository
import com.feature.user.domain.impl.GetUserCompletedChallengesUseCase
import com.feature.user.domain.repo.FakeUserRepository
import com.feature.user.util.createCompletedChallenge
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Test

internal class GetUserCompletedChallengesUseCaseTest {
    @Test
    fun `returns challenges from the repository`() {
        // Arrange
        val challenges = listOf(
            createCompletedChallenge(
                id = "1",
            ),
            createCompletedChallenge(
                id = "2",
            ),
        )
        val expectedFlow = flowOf(PagingData.from(challenges))
        val repository: UserRepository = FakeUserRepository(
            completedChallengesFlow = expectedFlow,
        )
        val sut = createSut(repository)

        // Act
        val result = sut()

        // Assert
        assertEquals(expectedFlow, result)
    }

    private fun createSut(repository: UserRepository) =
        GetUserCompletedChallengesUseCase(repository)
}
