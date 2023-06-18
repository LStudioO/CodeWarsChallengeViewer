@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.feature.user.ui.challenge.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.core.ui.preview.ThemePreviews
import com.core.ui.theme.CwTheme
import com.feature.user.R
import com.feature.user.ui.challenge.CompletedChallengeUiModel
import com.feature.user.ui.challenge.component.providers.CompletedChallengeProvider
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import kotlinx.collections.immutable.ImmutableList

@Composable
fun CompletedChallengeCard(
    modifier: Modifier = Modifier,
    cardModifier: Modifier = Modifier,
    orderNumber: Int,
    name: String,
    languages: ImmutableList<String>,
    completedAt: String,
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier,
    ) {
        Card(
            modifier = cardModifier
                .fillMaxWidth()
                .placeholder(
                    visible = isLoading,
                    shape = CardDefaults.shape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    highlight = PlaceholderHighlight.shimmer(),
                ),
            onClick = onClick,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                    )
                    .padding(all = 8.dp),
            ) {
                Row {
                    ItemBadge(
                        orderNumber = orderNumber,
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(),
                        text = name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(
                        id = R.string.completed_challenges_card_finished_date,
                        completedAt,
                    ),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    languages.forEach { language ->
                        key("language_$language") {
                            AssistChip(
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                    labelColor = MaterialTheme.colorScheme.onTertiaryContainer,
                                ),
                                onClick = {},
                                label = {
                                    Text(
                                        text = language,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                },
                            )
                        }
                    }
                }
            }
        }

        if (isLoading) {
            ItemBadge(
                modifier = Modifier.padding(8.dp),
                orderNumber = orderNumber,
            )
        }
    }
}

@Composable
private fun ItemBadge(
    modifier: Modifier = Modifier,
    orderNumber: Int,
) {
    Badge(
        modifier = modifier.padding(vertical = 2.dp),
        containerColor = MaterialTheme.colorScheme.onPrimary,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        content = {
            Text(
                modifier = Modifier.padding(4.dp),
                text = stringResource(
                    R.string.completed_challenges_card_number_placeholder,
                    orderNumber,
                ),
                style = MaterialTheme.typography.labelSmall,
            )
        },
    )
}

@ThemePreviews
@Composable
private fun CardPreview(
    @PreviewParameter(CompletedChallengeProvider::class)
    challenge: CompletedChallengeUiModel,
) {
    CwTheme {
        CompletedChallengeCard(
            orderNumber = 1,
            name = challenge.name,
            languages = challenge.completedLanguages,
            completedAt = challenge.completedAt,
            isLoading = false,
            onClick = {},
        )
    }
}
