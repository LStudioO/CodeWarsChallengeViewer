package com.core.ui.component

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.core.ui.preview.ThemePreviews
import com.core.ui.theme.CwTheme

@Composable
fun CwProgressBar(
    modifier: Modifier = Modifier,
) {
    CircularProgressIndicator(
        modifier = modifier,
    )
}

@ThemePreviews
@Composable
private fun CwProgressBarPreview() {
    CwTheme {
        CwProgressBar()
    }
}
