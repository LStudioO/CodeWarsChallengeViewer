@file:OptIn(ExperimentalMaterial3Api::class)

package com.core.ui.component

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.core.ui.icons.CwIcons
import com.core.ui.preview.ThemePreviews
import com.core.ui.theme.CwTheme

@Composable
fun CwTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    navigationIcon: ImageVector? = null,
    navigationIconContentDescription: String? = null,
    actionIcon: ImageVector? = null,
    actionIconContentDescription: String? = null,
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
            )
        },
        navigationIcon = {
            if (navigationIcon != null) {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = navigationIconContentDescription,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        actions = {
            if (actionIcon != null) {
                IconButton(onClick = onActionClick) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = actionIconContentDescription,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        modifier = modifier,
    )
}

@ThemePreviews
@Composable
private fun CwTopAppBarPreview() {
    CwTheme {
        CwTopAppBar(
            title = "Toolbar",
            navigationIcon = CwIcons.ArrowBack,
            navigationIconContentDescription = "Back",
            actionIcon = CwIcons.Settings,
            actionIconContentDescription = "Settings",
        )
    }
}
