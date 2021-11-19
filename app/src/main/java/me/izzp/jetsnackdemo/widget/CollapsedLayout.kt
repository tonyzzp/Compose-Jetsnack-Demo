package me.izzp.jetsnackdemo.widget

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import me.izzp.jetsnackdemo.*
import kotlin.math.roundToInt

private class VerticalScrollViewState {
    var offset by mutableStateOf(0f)
    var contentHeight by mutableStateOf(0f)
    var height by mutableStateOf(0f)
}

@Composable
private fun VerticalScrollView(
    state: VerticalScrollViewState,
    content: @Composable () -> Unit,
) {
    Layout(
        content = content,
        modifier = Modifier.clip(RectangleShape)
    ) { measurables, constraints ->
        val placeable = measurables.first().measure(
            constraints.copy(maxHeight = Constraints.Infinity)
        )
        state.contentHeight = placeable.height.toFloat()
        val width = constraints.constrainWidth(placeable.width)
        state.height = constraints.constrainHeight(placeable.height).toFloat()
        layout(width, state.height.roundToInt()) {
            placeable.placeRelative(0, state.offset.roundToInt())
        }
    }
}

@Composable
fun CollapsedLayout(
    header: ComposableFunction,
    icon: ComposableFunction,
    title: ComposableFunction,
    bottom: ComposableFunction,
    headerMaxHeight: Dp = 200.dp,
    headerMinHeight: Dp = 56.dp,
    content: ComposableFunction,
) {
    val density = LocalDensity.current
    val headerMax = with(density) { headerMaxHeight.roundToPx() }
    val headerMin = with(density) { headerMinHeight.roundToPx() }
    var containerMaxWidth by rememberInt()
    var containerMaxHeight by rememberInt()
    var headerHeight by rememberInt(headerMax)
    var bottomHeight by rememberInt(0)
    val fraction by derivedStateOf {
        val rtn = calcFraction(headerMin, headerMax, headerHeight)
        rtn
    }
    var titleHeight by rememberInt()
    val iconMaxSize by rememberDerivedStateOf {
        (containerMaxWidth * 0.7f).roundToInt()
    }
    val iconMinSize by rememberDerivedStateOf {
        (containerMaxWidth * 0.2f).coerceAtLeast(100 * density.density).roundToInt()
    }
    val iconSize by rememberDerivedStateOf {
        lerp(iconMinSize.toFloat(), iconMaxSize.toFloat(), fraction).roundToInt()
    }
    val titleTop by rememberDerivedStateOf {
        val max = headerHeight + iconSize / 2
        val min = headerHeight
        lerp(min.toFloat(), max.toFloat(), fraction)
    }
    val remainHeight by rememberDerivedStateOf {
        val rtn = containerMaxHeight - (titleTop + titleHeight) - bottomHeight
        rtn.roundToInt()
    }
    val iconAnchorX by rememberDerivedStateOf {
        lerp(0.8f, 0.5f, fraction)
    }
    val scrollViewState = remember { VerticalScrollViewState() }
    val realContent: ComposableFunction = {
        VerticalScrollView(content = content, state = scrollViewState)
    }
    val scrollHeader: (delta: Float) -> Float = remember {
        { delta ->
            val height = (headerHeight + delta).coerceIn(headerMin.toFloat(), headerMax.toFloat())
            val consumed = height - headerHeight
            headerHeight = height.roundToInt()
            consumed
        }
    }
    val scrollContent: (delta: Float) -> Float = remember {
        { delta ->
            val offset = (scrollViewState.offset + delta).coerceIn(
                scrollViewState.height - scrollViewState.contentHeight,
                0f
            )
            val consumed = (offset - scrollViewState.offset)
            scrollViewState.offset = offset
            consumed
        }
    }
    val scrollableState = rememberScrollableState { delta ->
        var consumed = 0f
        if (delta < 0f) {
            consumed = scrollHeader(delta)
            if (consumed > delta) {
                consumed += scrollContent(delta - consumed)
            }
        } else {
            consumed = scrollContent(delta)
            if (consumed < delta) {
                consumed += scrollHeader(delta - consumed)
            }
        }
        consumed
    }
    SubcomposeLayout(
        modifier = Modifier.scrollable(scrollableState, Orientation.Vertical)
    ) { constraints ->
        containerMaxWidth = constraints.maxWidth
        containerMaxHeight = constraints.maxHeight

        val measurableHeader = subcompose("header", header).first()
        val measurableIcon = subcompose("icon", icon).first()
        val measurableTitle = subcompose("title", title).first()
        val measurableContent = subcompose("content", realContent).first()
        val measurableBottom = subcompose("bottom", bottom).first()

        val placeableHeader = measurableHeader.measure(
            constraints.copy(minHeight = headerHeight, maxHeight = headerHeight)
        )

        val placeableIcon = measurableIcon.measure(
            constraints.copy(minWidth = iconSize, maxWidth = iconSize)
        )

        val placeableTitle = measurableTitle.measure(constraints)
        titleHeight = placeableTitle.height

        val placeableBottom = measurableBottom.measure(constraints)
        bottomHeight = placeableBottom.height

        val placeableContent = measurableContent.measure(
            constraints.copy(maxHeight = remainHeight)
        )
        val totalHeight =
            bottomHeight + placeableContent.height + titleHeight + iconSize + (headerHeight - iconSize / 2)
        layout(
            constraints.maxWidth,
            constraints.constrainHeight(totalHeight)
        ) {
            placeableHeader.placeRelative(0, 0)
            placeableIcon.placeRelative(
                (iconAnchorX * containerMaxWidth - iconSize / 2).roundToInt(),
                headerHeight - iconSize / 2
            )
            placeableTitle.placeRelative(
                0,
                titleTop.roundToInt()
            )
            placeableContent.placeRelative(
                0,
                titleTop.roundToInt() + titleHeight
            )
            placeableBottom.placeRelative(
                0,
                titleTop.roundToInt() + titleHeight + placeableContent.height
            )
        }
    }
}
