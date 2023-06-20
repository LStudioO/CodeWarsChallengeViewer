package com.feature.challenge_details.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.core.ui.component.GaugeChart
import com.core.ui.preview.ThemePreviews
import com.core.ui.theme.CwTheme

@Composable
internal fun SuccessRatioCard(
    modifier: Modifier = Modifier,
    title: String,
    percentValue: Int,
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
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            GaugeChart(
                modifier = Modifier.padding(8.dp),
                percentValue = percentValue,
                primaryColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }
}

@ThemePreviews
@Composable
private fun SuccessRatioCardPreview() {
    CwTheme {
        SuccessRatioCard(
            modifier = Modifier
                .width(160.dp),
            title = "Rate",
            percentValue = 57,
        )
    }
}
