package com.feature.challenge_details.data.local

import com.core.utils.NotFoundError
import com.core.utils.functional.Either
import com.core.utils.logger.AppLogger
import com.core.utils.platform.network.HttpError
import com.feature.challenge_details.data.remote.api.ChallengeApi
import com.feature.challenge_details.util.createChallengeDetails
import com.feature.challenge_details.util.createChallengeDetailsDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.test.assertSame

internal class DefaultChallengeRepositoryTest {
    @Test
    fun `returns the challenge details from the api`() = runTest {
        // Arrange
        val expectedChallenge = createChallengeDetails(id = "1")
        val sut = createSut(
            api = mockk {
                coEvery { getDetails(id = "1") } returns Either.Right(
                    createChallengeDetailsDto(id = "1"),
                )
            },
        )

        // Act
        val result = sut.getDetails("1")

        // Assert
        assertEquals(expectedChallenge, result.orNull())
    }

    @Test
    fun `handles 404 response`() = runTest {
        // Arrange
        val sut = createSut(
            api = mockk {
                coEvery { getDetails(id = "1") } returns Either.Left(
                    HttpError(body = "", code = 404),
                )
            },
        )

        // Act
        val result = sut.getDetails("1")

        // Assert
        assertSame(NotFoundError, result.leftOrNull())
    }

    private fun createSut(
        api: ChallengeApi = mockk(),
        mapper: com.feature.challenge_details.data.mapper.ChallengeDetailsMapper =
            com.feature.challenge_details.data.mapper.ChallengeDetailsMapper(),
        logger: AppLogger = mockk(relaxUnitFun = true),
    ): DefaultChallengeRepository {
        return DefaultChallengeRepository(
            api = api,
            mapper = mapper,
            logger = logger,
        )
    }
}
