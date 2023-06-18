package com.feature.user.ui.challenge

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.testing.asPagingSourceFactory
import androidx.paging.testing.asSnapshot
import com.feature.user.domain.api.UserRepository
import com.feature.user.domain.impl.GetUserCompletedChallengesUseCase
import com.feature.user.domain.repo.FakeUserRepository
import com.feature.user.ui.challenge.mapper.CompletedChallengeMapper
import com.feature.user.util.MainDispatcherRule
import com.feature.user.util.createCompletedChallenge
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

internal class CompletedChallengesViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `maps challenges to ui`() = runTest {
        // Arrange
        val challenges = listOf(
            createCompletedChallenge(
                id = "1",
            ),
            createCompletedChallenge(
                id = "2",
            ),
        )
        val dataFactory = challenges.asPagingSourceFactory()
        val pager = Pager(
            config = PagingConfig(
                pageSize = 2,
                initialLoadSize = 2,
                prefetchDistance = 0,
            ),
            pagingSourceFactory = dataFactory,
        )
        val repository: UserRepository = FakeUserRepository(
            completedChallengesFlow = pager.flow,
        )
        val sut = createSut(
            getUserChallenges = GetUserCompletedChallengesUseCase(
                repository = repository,
            ),
        )

        // Act
        val snapshot = sut.challenges.asSnapshot()

        // Assert
        assertEquals(snapshot.map { it.id }, listOf("1", "2"))
    }

    private fun createSut(
        getUserChallenges: GetUserCompletedChallengesUseCase,
    ) = CompletedChallengesViewModel(
        getCompletedChallenges = getUserChallenges,
        mapper = CompletedChallengeMapper(),
    )
}
