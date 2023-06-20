package com.feature.challenge_details.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.utils.NotFoundError
import com.feature.challenge_details.domain.usecase.GetChallengeDetailsUseCase
import com.feature.challenge_details.ui.mapper.ChallengeDetailsMapper
import com.feature.challenge_details.ui.model.ChallengeDetailsError
import com.feature.challenge_details.ui.model.ChallengeDetailsScreenUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ChallengeDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getChallengeDetails: GetChallengeDetailsUseCase,
    private val mapper: ChallengeDetailsMapper,
) : ViewModel() {
    private val challengeId: String by lazy { checkNotNull(savedStateHandle[CHALLENGE_ID]) }
    private val challengeName: String by lazy { checkNotNull(savedStateHandle[CHALLENGE_NAME]) }

    private val _screenFlow = MutableStateFlow(
        ChallengeDetailsScreenUiModel(
            isLoading = true,
            error = ChallengeDetailsError.None,
            challengeDetails = null,
            challengeName = challengeName,
        ),
    )

    val screenFlow: StateFlow<ChallengeDetailsScreenUiModel> = _screenFlow

    init {
        loadDetails()
    }

    private fun loadDetails() {
        _screenFlow.update { model -> model.copy(isLoading = true) }
        viewModelScope.launch {
            getChallengeDetails(challengeId).fold(
                ifLeft = { error ->
                    val uiError = if (error is NotFoundError) {
                        ChallengeDetailsError.NotFound
                    } else {
                        ChallengeDetailsError.NetworkError
                    }
                    _screenFlow.update { model ->
                        model.copy(isLoading = false, error = uiError, challengeDetails = null)
                    }
                },
                ifRight = { details ->
                    _screenFlow.update { model ->
                        model.copy(
                            isLoading = false, error = ChallengeDetailsError.None,
                            challengeDetails = mapper.toUi(details),
                        )
                    }
                },
            )
        }
    }

    fun onRetryClick() {
        loadDetails()
    }
}
