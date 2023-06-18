package com.feature.user.ui.challenge.component.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.feature.user.ui.challenge.CompletedChallengeUiModel
import kotlinx.collections.immutable.persistentListOf

class CompletedChallengeProvider : PreviewParameterProvider<CompletedChallengeUiModel> {
    override val values: Sequence<CompletedChallengeUiModel>
        get() = sequenceOf(
            CompletedChallengeUiModel(
                id = "1",
                name = "Operator overload ?",
                completedLanguages = persistentListOf("javascript"),
                completedAt = "2016-04-02",
            ),
            CompletedChallengeUiModel(
                id = "2",
                name = "Populate hash with array keys and default value",
                completedLanguages = persistentListOf(
                    "javascript", "kotlin", "java", "python",
                    "swift", "haskel",
                ),
                completedAt = "2016-04-02",
            ),
        )
}
