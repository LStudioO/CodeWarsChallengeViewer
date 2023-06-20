package com.feature.challenge_details.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.core.ui.preview.ThemePreviews
import com.core.ui.theme.CwTheme

@Composable
fun CwRank(
    modifier: Modifier = Modifier,
    value: String,
    color: Color,
    containerColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
) {
    AssistChip(
        modifier = modifier,
        shape = CutCornerShape(8.dp),
        elevation = AssistChipDefaults.elevatedAssistChipElevation(
            elevation = 8.dp,
        ),
        colors = AssistChipDefaults.assistChipColors(
            containerColor = containerColor,
        ),
        border = AssistChipDefaults.assistChipBorder(
            borderColor = color,
            borderWidth = 2.dp,
        ),
        label = {
            Text(
                text = value,
                textAlign = TextAlign.Center,
                color = color,
                modifier = Modifier
                    .padding(4.dp),
            )
        },
        onClick = { },
    )
}

@ThemePreviews
@Composable
fun CwRankPreview() {
    CwTheme {
        CwRank(
            value = "7 kyu",
            color = Color.Red,
        )
    }
}
