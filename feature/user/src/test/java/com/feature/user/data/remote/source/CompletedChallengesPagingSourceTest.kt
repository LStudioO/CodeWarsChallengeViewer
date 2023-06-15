package com.feature.user.data.remote.source

import androidx.paging.PagingConfig
import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import androidx.paging.PagingState
import com.core.utils.functional.Either
import com.core.utils.logger.AppLogger
import com.core.utils.platform.network.NetworkError
import com.feature.user.createCompletedChallengeDto
import com.feature.user.data.remote.api.API_PAGE_COUNT
import com.feature.user.data.remote.api.FakeUserApi
import com.feature.user.data.remote.api.TEST_USER
import com.feature.user.data.remote.api.UserApi
import com.feature.user.data.remote.dto.CompletedChallengeDto
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

@ExperimentalCoroutinesApi
class CompletedChallengesPagingSourceTest {

    @Test
    fun `refreshKey returns the first page`() {
        // Arrange
        val challenge1 = createCompletedChallengeDto(id = "1")
        val challenge2 = createCompletedChallengeDto(id = "2")
        val challenge3 = createCompletedChallengeDto(id = "3")
        val sut = createSut()
        val page0 = LoadResult.Page(
            data = listOf(challenge1, challenge2),
            prevKey = null,
            nextKey = 1,
        )
        val page1 = LoadResult.Page(
            data = listOf(challenge3),
            prevKey = 0,
            nextKey = null,
        )
        val state: PagingState<Int, CompletedChallengeDto> = PagingState(
            pages = listOf(page0, page1),
            anchorPosition = 1,
            config = PagingConfig(
                pageSize = API_PAGE_COUNT,
            ),
            leadingPlaceholderCount = 0,
        )

        // Act
        val refreshKey = sut.getRefreshKey(state)

        // Assert
        assertEquals(0, refreshKey)
    }

    @Test
    fun `refreshKey returns the mid page`() {
        // Arrange
        val challenge1 = createCompletedChallengeDto(id = "1")
        val challenge2 = createCompletedChallengeDto(id = "2")
        val challenge3 = createCompletedChallengeDto(id = "3")
        val challenge4 = createCompletedChallengeDto(id = "4")
        val challenge5 = createCompletedChallengeDto(id = "5")
        val sut = createSut()
        val page0 = LoadResult.Page(
            data = listOf(challenge1, challenge2),
            prevKey = null,
            nextKey = 1,
        )
        val page1 = LoadResult.Page(
            data = listOf(challenge3, challenge4),
            prevKey = 0,
            nextKey = 2,
        )
        val page3 = LoadResult.Page(
            data = listOf(challenge5),
            prevKey = 1,
            nextKey = null,
        )
        val state: PagingState<Int, CompletedChallengeDto> = PagingState(
            pages = listOf(page0, page1, page3),
            anchorPosition = 3,
            config = PagingConfig(
                pageSize = API_PAGE_COUNT,
            ),
            leadingPlaceholderCount = 0,
        )

        // Act
        val refreshKey = sut.getRefreshKey(state)

        // Assert
        assertEquals(1, refreshKey)
    }

    @Test
    fun `refreshKey returns the last page`() {
        // Arrange
        val challenge1 = createCompletedChallengeDto(id = "1")
        val challenge2 = createCompletedChallengeDto(id = "2")
        val challenge3 = createCompletedChallengeDto(id = "3")
        val sut = createSut()
        val page0 = LoadResult.Page(
            data = listOf(challenge1, challenge2),
            prevKey = null,
            nextKey = 1,
        )
        val page1 = LoadResult.Page(
            data = listOf(challenge3),
            prevKey = 0,
            nextKey = null,
        )
        val state: PagingState<Int, CompletedChallengeDto> = PagingState(
            pages = listOf(page0, page1),
            anchorPosition = 2,
            config = PagingConfig(
                pageSize = API_PAGE_COUNT,
            ),
            leadingPlaceholderCount = 0,
        )

        // Act
        val refreshKey = sut.getRefreshKey(state)

        // Assert
        assertEquals(1, refreshKey)
    }

    @Test
    fun `successfully loads the first page`() = runTest {
        // Arrange
        val challenge1 = createCompletedChallengeDto(id = "1")
        val challenge2 = createCompletedChallengeDto(id = "2")
        val challenge3 = createCompletedChallengeDto(id = "3")
        val userApi = createUserApi().apply {
            listOf(challenge1, challenge2, challenge3).forEach { challenge ->
                addCompletedChallenge(
                    username = TEST_USER,
                    item = challenge,
                )
            }
        }
        val sut = createSut(userApi = userApi)
        val params: LoadParams<Int> = mockk(relaxed = true)
        val page = 0
        val size = API_PAGE_COUNT
        every { params.key } returns page
        every { params.loadSize } returns size
        every { params.placeholdersEnabled } returns false

        val resultData: List<CompletedChallengeDto> = listOf(challenge1, challenge2)

        // Act
        val loadResult = sut.load(params)

        // Assert
        assertNotNull(loadResult)
        assertEquals(
            LoadResult.Page(
                data = resultData,
                prevKey = null,
                nextKey = 1,
            ),
            loadResult,
        )
    }

    @Test
    fun `adds placeholders for the next page`() = runTest {
        // Arrange
        val challenge1 = createCompletedChallengeDto(id = "1")
        val challenge2 = createCompletedChallengeDto(id = "2")
        val challenge3 = createCompletedChallengeDto(id = "3")
        val userApi = createUserApi().apply {
            listOf(challenge1, challenge2, challenge3).forEach { challenge ->
                addCompletedChallenge(
                    username = TEST_USER,
                    item = challenge,
                )
            }
        }
        val sut = createSut(userApi = userApi)
        val params: LoadParams<Int> = mockk(relaxed = true)
        val page = 0
        val size = API_PAGE_COUNT
        every { params.key } returns page
        every { params.loadSize } returns size
        every { params.placeholdersEnabled } returns true

        val resultData: List<CompletedChallengeDto> = listOf(challenge1, challenge2)

        // Act
        val loadResult = sut.load(params)

        // Assert
        assertNotNull(loadResult)
        assertEquals(
            LoadResult.Page(
                data = resultData,
                prevKey = null,
                nextKey = 1,
                itemsBefore = 0,
                itemsAfter = 1,
            ),
            loadResult,
        )
    }

    @Test
    fun `returns the error when a network request fails`() = runTest {
        // Arrange
        val userApi = mockk<UserApi>()
        val sut = createSut(userApi)
        val params: LoadParams<Int> = mockk(relaxed = true)
        val page = 0
        every { params.key } returns page

        val exception = Exception("Test exception")
        coEvery { userApi.getCompletedChallenges(TEST_USER, page) } returns Either.left(
            NetworkError(exception),
        )

        // Act
        val loadResult = sut.load(params)

        // Assert
        assertNotNull(loadResult)
        assertEquals(
            LoadResult.Error<Int, CompletedChallengeDto>(NetworkError(exception)),
            loadResult,
        )
    }

    private fun createUserApi(): FakeUserApi = FakeUserApi()
    private fun createLogger(): AppLogger = mockk(relaxUnitFun = true)

    private fun createSut(
        userApi: UserApi = createUserApi(),
        logger: AppLogger = createLogger(),
    ): CompletedChallengesPagingSource {
        return CompletedChallengesPagingSource(
            userApi = userApi,
            userName = TEST_USER,
            logger = logger,
        )
    }
}
