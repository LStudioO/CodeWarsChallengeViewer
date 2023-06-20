package com.feature.challenge_details.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.core.ui.preview.ThemePreviews
import com.core.ui.theme.CwTheme

@Composable
internal fun BasicStatisticCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier.padding(4.dp),
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }
}

@ThemePreviews
@Composable
private fun BasicStatisticCardPreview() {
    CwTheme {
        BasicStatisticCard(
            modifier = Modifier
                .width(200.dp),
            title = "Unresolved suggestions",
            value = "125",
        )
    }
}
