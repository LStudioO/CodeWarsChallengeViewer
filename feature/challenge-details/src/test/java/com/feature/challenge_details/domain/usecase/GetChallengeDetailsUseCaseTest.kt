package com.feature.challenge_details.domain.usecase

import com.core.utils.functional.Either
import com.feature.challenge_details.domain.repository.ChallengeRepository
import com.feature.challenge_details.util.createChallengeDetails
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

internal class GetChallengeDetailsUseCaseTest {
    @Test
    fun `returns the challenge details from the repository`() = runTest {
        // Arrange
        val challenge = createChallengeDetails(
            id = "1",
        )
        val repository: ChallengeRepository = mockk {
            coEvery { getDetails(id = "1") } returns Either.Right(challenge)
        }
        val sut = createSut(repository)

        // Act
        val result = sut("1")

        // Assert
        assertEquals(challenge, result.orNull())
    }

    private fun createSut(repository: ChallengeRepository) =
        GetChallengeDetailsUseCase(repository)
}
