package com.feature.challenge_details.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.core.ui.preview.ThemePreviews
import com.core.ui.theme.CwTheme

@Composable
internal fun Tag(
    modifier: Modifier = Modifier,
    value: String,
) {
    AssistChip(
        modifier = modifier,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            labelColor = MaterialTheme.colorScheme.onTertiaryContainer,
        ),
        shape = CutCornerShape(8.dp),
        elevation = AssistChipDefaults.elevatedAssistChipElevation(
            elevation = 8.dp,
        ),
        onClick = {},
        leadingIcon = {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Rounded.Category,
                contentDescription = "TODO",
                tint = MaterialTheme.colorScheme.onSurface,
            )
        },
        label = {
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
    )
}

@ThemePreviews
@Composable
private fun TagPreview() {
    CwTheme {
        Tag(
            value = "Programming",
        )
    }
}
