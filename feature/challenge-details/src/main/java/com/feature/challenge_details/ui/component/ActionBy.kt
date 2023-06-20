package com.feature.challenge_details.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Man
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.core.ui.preview.ThemePreviews
import com.core.ui.theme.CwTheme
import com.feature.challenge_details.R

@Composable
internal fun ActionBy(
    modifier: Modifier = Modifier,
    name: String,
    icon: ImageVector,
    date: String?,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.clickable {
            onClick.invoke()
        },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            modifier = modifier,
            text = name,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        Spacer(modifier = Modifier.size(4.dp))
        if (date != null) {
            Text(
                modifier = modifier,
                text = stringResource(id = R.string.challenge_details_action_by_date, date),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }
}

@ThemePreviews
@Composable
private fun ActionByPreview() {
    CwTheme {
        ActionBy(
            name = "Alex",
            icon = Icons.Default.Man,
            date = "2022-02-01",
            onClick = {},
        )
    }
}

@ThemePreviews
@Composable
private fun ActionByNoDatePreview() {
    CwTheme {
        ActionBy(
            name = "michael03",
            icon = Icons.Default.Man,
            date = null,
            onClick = {},
        )
    }
}
