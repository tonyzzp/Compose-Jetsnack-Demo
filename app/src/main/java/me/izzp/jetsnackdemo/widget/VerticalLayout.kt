package me.izzp.jetsnackdemo.widget

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import me.izzp.jetsnackdemo.ComposableFunction
import kotlin.math.roundToInt

@Composable
fun VerticalLayout(
    modifier: Modifier = Modifier,
    space: Dp = 0.dp,
    content: ComposableFunction,
) {
    var height by remember { mutableStateOf(0) }
    var contentHeight by remember { mutableStateOf(0) }
    var offset by remember { mutableStateOf(0f) }
    val scrollableState = rememberScrollableState { delta ->
        val result = (offset + delta).coerceIn(height - contentHeight.toFloat(), 0f)
        val rtn = result - offset
        offset = result
        rtn
    }
    val spacing = with(LocalDensity.current) { space.roundToPx() }
    SubcomposeLayout(
        modifier = modifier.scrollable(scrollableState, Orientation.Vertical).clip(RectangleShape),
    ) { constraints ->
        val placeables = subcompose(
            "content",
            content
        ).map { it.measure(constraints.copy(maxHeight = Constraints.Infinity)) }
        val contentWidth = placeables.maxOf { it.width }
        contentHeight = placeables.sumOf { it.height } + maxOf(placeables.size - 1, 0) * spacing
        val width = constraints.constrainWidth(contentWidth)
        height = constraints.constrainHeight(contentHeight)
        layout(width, height) {
            var y = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(0, y + offset.roundToInt())
                y += placeable.height + spacing
            }
        }
    }
}