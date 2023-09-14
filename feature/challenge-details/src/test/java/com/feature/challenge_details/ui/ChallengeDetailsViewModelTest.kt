@file:OptIn(ExperimentalCoroutinesApi::class)

package com.feature.challenge_details.ui

import androidx.lifecycle.SavedStateHandle
import com.core.utils.AppError
import com.core.utils.NotFoundError
import com.core.utils.functional.Either
import com.core.utils.testing.MainDispatcherRule
import com.feature.challenge_details.data.local.ChallengeRepositoryFake
import com.feature.challenge_details.domain.usecase.GetChallengeDetailsUseCase
import com.feature.challenge_details.ui.mapper.ChallengeDetailsMapper
import com.feature.challenge_details.ui.model.ChallengeDetailsError
import com.feature.challenge_details.util.createChallengeDetails
import com.feature.challenge_details.util.createChallengeDetailsScreenUiModel
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ChallengeDetailsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(
        testDispatcher = StandardTestDispatcher(),
    )

    @Test
    fun `initial state is loading`() = runTest {
        // Arrange
        val expectedState = createChallengeDetailsScreenUiModel(
            isLoading = true,
        )
        val sut = createSut(
            getChallengeDetails = GetChallengeDetailsUseCase(
                repository = ChallengeRepositoryFake(),
            ),
        )

        // Assert
        assertEquals(
            expectedState,
            sut.screenFlow.value,
        )
    }

    @Test
    fun `details loading starts on init`() = runTest {
        // Arrange
        val sut = createSut(
            getChallengeDetails = GetChallengeDetailsUseCase(
                repository = ChallengeRepositoryFake().apply {
                    setDetailsResponse(
                        Either.Right(
                            createChallengeDetails(),
                        ),
                    )
                },
            ),
        )

        // Act
        runCurrent()

        // Assert
        assertNotNull(sut.screenFlow.value.challengeDetails)
    }

    @Test
    fun `challenge name is parsed from saved state`() = runTest {
        // Arrange
        val sut = createSut(
            savedStateHandle = SavedStateHandle(
                mapOf(CHALLENGE_ID to "id", CHALLENGE_NAME to "name"),
            ),
            getChallengeDetails = GetChallengeDetailsUseCase(
                repository = ChallengeRepositoryFake(),
            ),
        )

        // Assert
        assertEquals("name", sut.screenFlow.value.challengeName)
    }

    @Test
    fun `network error happens while loading`() = runTest {
        // Arrange
        val expectedState = createChallengeDetailsScreenUiModel(
            error = ChallengeDetailsError.NetworkError,
        )
        val sut = createSut(
            getChallengeDetails = GetChallengeDetailsUseCase(
                repository = ChallengeRepositoryFake().apply {
                    setDetailsResponse(Either.Left(AppError()))
                },
            ),
        )

        // Act
        runCurrent()

        // Assert
        assertEquals(expectedState, sut.screenFlow.value)
    }

    @Test
    fun `not found error happens while loading`() = runTest {
        // Arrange
        val expectedState = createChallengeDetailsScreenUiModel(
            error = ChallengeDetailsError.NotFound,
        )
        val sut = createSut(
            getChallengeDetails = GetChallengeDetailsUseCase(
                repository = ChallengeRepositoryFake().apply {
                    setDetailsResponse(Either.Left(NotFoundError))
                },
            ),
        )

        // Act
        runCurrent()

        // Assert
        assertEquals(expectedState, sut.screenFlow.value)
    }

    @Test
    fun `successfully retry`() = runTest {
        // Arrange
        val repository = ChallengeRepositoryFake().apply {
            setDetailsResponse(Either.Left(AppError()))
        }
        val sut = createSut(
            getChallengeDetails = GetChallengeDetailsUseCase(
                repository = repository,
            ),
        )
        // Execute initial loading with the error response
        runCurrent()
        // Set the success response for the retry attempt
        repository.setDetailsResponse(
            Either.Right(
                createChallengeDetails(),
            ),
        )

        // Act
        sut.onRetryClick()
        runCurrent()

        // Assert
        assertNotNull(sut.screenFlow.value.challengeDetails)
    }

    @Test
    fun `loading starts when retry`() = runTest {
        // Arrange
        val sut = createSut(
            getChallengeDetails = GetChallengeDetailsUseCase(
                repository = ChallengeRepositoryFake().apply {
                    setDetailsResponse(Either.Left(AppError()))
                },
            ),
        )
        // Execute initial loading with the error response
        runCurrent()

        // Act
        sut.onRetryClick()

        // Assert
        assertTrue(sut.screenFlow.value.isLoading)
    }

    @Test
    fun `network error happens while retrying`() = runTest {
        // Arrange
        val sut = createSut(
            getChallengeDetails = GetChallengeDetailsUseCase(
                repository = ChallengeRepositoryFake().apply {
                    setDetailsResponse(Either.Left(AppError()))
                },
            ),
        )
        // Execute initial loading with the error response
        runCurrent()

        // Act
        sut.onRetryClick()
        runCurrent()

        // Assert
        assertEquals(ChallengeDetailsError.NetworkError, sut.screenFlow.value.error)
    }

    @Test
    fun `not found error happens while retrying`() = runTest {
        // Arrange
        val sut = createSut(
            getChallengeDetails = GetChallengeDetailsUseCase(
                repository = ChallengeRepositoryFake().apply {
                    setDetailsResponse(Either.Left(NotFoundError))
                },
            ),
        )
        // Execute initial loading with the error response
        runCurrent()

        // Act
        sut.onRetryClick()
        runCurrent()

        // Assert
        assertEquals(ChallengeDetailsError.NotFound, sut.screenFlow.value.error)
    }

    private fun createSut(
        savedStateHandle: SavedStateHandle = SavedStateHandle(
            mapOf(CHALLENGE_ID to "", CHALLENGE_NAME to ""),
        ),
        mapper: ChallengeDetailsMapper = ChallengeDetailsMapper(),
        getChallengeDetails: GetChallengeDetailsUseCase = mockk(),
    ) = ChallengeDetailsViewModel(
        savedStateHandle = savedStateHandle,
        mapper = mapper,
        getChallengeDetails = getChallengeDetails,
    )
}
