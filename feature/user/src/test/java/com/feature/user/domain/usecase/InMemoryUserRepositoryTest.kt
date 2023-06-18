package com.feature.user.domain.usecase

import androidx.paging.testing.asSnapshot
import com.feature.user.data.local.InMemoryUserRepository
import com.feature.user.data.local.data_source.UserDataSource
import com.feature.user.data.mapper.CompletedChallengeMapper
import com.feature.user.data.remote.api.FakeUserApi
import com.feature.user.data.remote.api.TEST_USER
import com.feature.user.data.remote.api.UserApi
import com.feature.user.domain.model.User
import com.feature.user.util.createCompletedChallenge
import com.feature.user.util.createCompletedChallengeDto
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

internal class InMemoryUserRepositoryTest {
    @Test
    fun `returns challenges from the api`() = runTest {
        // Arrange
        val api = FakeUserApi()
        listOf(
            createCompletedChallengeDto(
                id = "1",
            ),
            createCompletedChallengeDto(
                id = "2",
            ),
        ).forEach { challenge ->
            api.addCompletedChallenge(
                username = TEST_USER,
                item = challenge,
            )
        }
        val expectedChallenges = listOf(
            createCompletedChallenge(
                id = "1",
            ),
            createCompletedChallenge(
                id = "2",
            ),
        )
        val sut = createSut(
            userApi = api,
            userDataSource = mockk {
                every { user } returns User(TEST_USER)
            },
        )

        // Act
        val result = sut.getCompletedChallenges().asSnapshot()

        // Assert
        assertEquals(expectedChallenges, result)
    }

    private fun createSut(
        userApi: UserApi = mockk(),
        userDataSource: UserDataSource = mockk(),
    ): InMemoryUserRepository {
        return InMemoryUserRepository(
            userApi = userApi,
            completedChallengeMapper = CompletedChallengeMapper(),
            userDataSource = userDataSource,
            logger = mockk(relaxUnitFun = true),
        )
    }
}
