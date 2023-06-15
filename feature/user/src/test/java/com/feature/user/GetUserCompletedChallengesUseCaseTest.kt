package com.feature.user

import com.core.utils.functional.Either
import com.feature.user.domain.api.UserRepository
import com.feature.user.domain.impl.GetUserCompletedChallengesUseCase
import com.feature.user.domain.model.CompletedChallenges
import com.feature.user.repository.FakeUserRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

internal class GetUserCompletedChallengesUseCaseTest {
    @Test
    fun `returns challenges from the repository`() = runTest {
        // Arrange
        val challenges = Either.right(CompletedChallenges.emptyInstance())
        val repository: UserRepository = FakeUserRepository(
            completedChallengesState = challenges,
        )
        val sut = createSut(repository)

        // Act
        val result = sut()

        // Assert
        assertEquals(challenges, result)
    }

    private fun createSut(repository: UserRepository) =
        GetUserCompletedChallengesUseCase(repository)
}
