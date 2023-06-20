package com.feature.challenge_details.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.core.ui.preview.ThemePreviews
import com.core.ui.theme.CwTheme
import com.feature.challenge_details.R

@Composable
internal fun Category(
    modifier: Modifier = Modifier,
    value: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Rounded.Category,
            contentDescription = stringResource(
                id = R.string.challenge_details_category_content_description,
            ),
            tint = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            modifier = modifier,
            text = value,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    }
}

@ThemePreviews
@Composable
private fun CategoryPreview() {
    CwTheme {
        Category(
            value = "Programming",
        )
    }
}
