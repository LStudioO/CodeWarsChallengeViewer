package com.core.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.core.ui.preview.ThemePreviews
import com.core.ui.theme.CwTheme

@Composable
fun CwBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
        content = content,
    )
}

@ThemePreviews
@Composable
private fun CwBackgroundPreview() {
    CwTheme {
        CwBackground(modifier = Modifier.size(50.dp), content = {})
    }
}
