/*
 * Copyright 2022 Adam McNeilly
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.core.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import com.core.ui.preview.ThemePreviews
import com.core.ui.theme.CwTheme

@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    numColumns: Int = 2,
    content: @Composable () -> Unit,
) {
    Layout(
        content = content,
        modifier = modifier,
    ) { measurables, constraints ->

        // Width per column based on constraints
        val columnWidth = (constraints.maxWidth / numColumns)

        // Make sure items can't be wider than column width
        val itemConstraints = constraints.copy(maxWidth = columnWidth)

        // Track the height of each column in an array.
        val columnHeights = IntArray(numColumns) { 0 }

        // For each item to place, figure out the shortest column so we know where to place it
        // and keep track of how large that column is going to be.
        val placeables = measurables.map { measurable ->
            val column = shortestColumn(columnHeights)
            val placeable = measurable.measure(itemConstraints)
            columnHeights[column] += placeable.height
            placeable
        }

        // Get the height of the entire grid, by using the largest column,
        // ensuring that it does not go out of the bounds
        // of this container.
        // If something went wrong, default to min height.
        val height = columnHeights.maxOrNull()
            ?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight

        layout(
            width = constraints.maxWidth,
            height = height,
        ) {
            // Keep track of the current Y position for each column
            val columnYPointers = IntArray(numColumns) { 0 }

            placeables.forEach { placeable ->
                // Determine which column to place this item in
                val column = shortestColumn(columnYPointers)

                placeable.place(
                    x = columnWidth * column,
                    y = columnYPointers[column],
                )

                // Update the pointer for this column based on the item
                // we just placed.
                columnYPointers[column] += placeable.height
            }
        }
    }
}

/**
 * Loop through all of the column heights, and determine which one is the shortest. This is how
 * we determine which column to add the next item to.
 */
private fun shortestColumn(columnHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var columnIndex = 0

    columnHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            columnIndex = index
        }
    }

    return columnIndex
}

@ThemePreviews
@Composable
private fun StaggeredVerticalGridPreview() {
    val texts = listOf("Lorem", "Lorem Ipsum Lorem Ipsum", "Ipsum", "Ipsum")
    CwTheme {
        StaggeredVerticalGrid(
            numColumns = 2,
        ) {
            texts.forEach { text ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    colors = CardDefaults.cardColors(),
                ) {
                    Text(
                        text = text,
                        modifier = Modifier.padding(16.dp),
                    )
                }
            }
        }
    }
}
