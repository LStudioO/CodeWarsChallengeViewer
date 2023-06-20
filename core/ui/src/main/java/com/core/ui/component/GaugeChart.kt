@file:OptIn(ExperimentalTextApi::class)

package com.core.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.ui.preview.ThemePreviews
import com.core.ui.theme.CwTheme

@Composable
fun GaugeChart(
    modifier: Modifier = Modifier,
    percentValue: Int,
    primaryColor: Color,
    needleSize: Dp = 3.dp,
    arcWidth: Dp = 10.dp,
    arcBrush: Brush = Brush.horizontalGradient(
        0.1f to Color.Red,
        0.5f to Color.Yellow,
        0.6f to Color.Green,
    ),
    textMargin: Dp = 8.dp,
    textStyle: TextStyle = TextStyle(
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        textAlign = TextAlign.Center,
    ),
) {
    val safePercentValue = percentValue.coerceAtLeast(0).coerceAtMost(100)
    val safePercentString = "$safePercentValue%"
    val density = LocalDensity.current

    // Arc
    val gaugeDegrees = 180
    val startAngle = 180f
    val arcWidthPx = with(density) { arcWidth.toPx() }

    // Needle
    val needleSizePx = with(density) { needleSize.toPx() }
    val needlePaint = remember { Paint().apply { color = primaryColor } }

    // Text
    val textMeasure = rememberTextMeasurer()
    val textSize = textMeasure.measure(safePercentString).size
    val textWidth = textSize.width
    val textHeight = textSize.height

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val width = constraints.maxWidth.toFloat()
        val height = (constraints.maxWidth / 2) + textHeight + with(
            density,
        ) { textMargin.toPx() }
        val canvasWidthDp = with(density) { width.toDp() }
        val canvasHeightDp = with(density) { height.toDp() }
        val center = Offset(width / 2, width / 2)

        Canvas(
            modifier = Modifier
                .width(canvasWidthDp)
                .height(canvasHeightDp),
            onDraw = {
                drawArc(
                    brush = arcBrush,
                    startAngle = startAngle,
                    sweepAngle = gaugeDegrees.toFloat(),
                    useCenter = false,
                    size = Size(width, width),
                    style = Stroke(width = arcWidthPx, cap = StrokeCap.Round),
                )

                drawIntoCanvas { canvas ->
                    canvas.save()

                    // Rotate the needle base on the percent
                    canvas.rotate(
                        degrees = gaugeDegrees * percentValue / 100f,
                        pivotX = center.x,
                        pivotY = center.y,
                    )

                    //Draw the needle
                    canvas.drawPath(
                        Path().apply {
                            moveTo(center.x, center.x)
                            lineTo(center.x, center.y + needleSizePx)
                            lineTo(width / 7, center.y)
                            lineTo(center.x, center.y)
                            close()
                        },
                        needlePaint,
                    )

                    canvas.restore()
                }

                drawText(
                    textMeasurer = textMeasure, text = safePercentString,
                    style = textStyle,
                    topLeft = Offset(center.x - textWidth, center.y),
                )
            },
        )
    }
}

@ThemePreviews
@Composable
private fun GaugeChartPreview() {
    CwTheme {
        GaugeChart(
            modifier = Modifier.padding(16.dp),
            percentValue = 80,
            primaryColor = MaterialTheme.colorScheme.onPrimary,
        )
    }
}
