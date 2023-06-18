package com.core.ui.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme", group = "Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme", group = "Dark")
annotation class ThemePreviews
