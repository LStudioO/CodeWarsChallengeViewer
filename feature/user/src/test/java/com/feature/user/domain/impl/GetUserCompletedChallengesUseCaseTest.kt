package com.feature.user.domain.impl

import androidx.paging.PagingData
import com.feature.user.createCompletedChallenge
import com.feature.user.domain.api.FakeUserRepository
import com.feature.user.domain.api.UserRepository
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
