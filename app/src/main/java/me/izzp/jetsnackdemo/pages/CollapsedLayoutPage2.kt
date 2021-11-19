package me.izzp.jetsnackdemo.pages

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import me.izzp.jetsnackdemo.ComposableFunction
import me.izzp.jetsnackdemo.R
import me.izzp.jetsnackdemo.ui.theme.jetTheme

private val BottomBarHeight = 56.dp

private val HeaderMinHeight = 56.dp
private val HeaderMaxHeight = 200.dp

private val IconMaxSize = 300.dp
private val IconMinSize = 100.dp

private val IconMinOffset = 16.dp
private val IconMaxOffset = HeaderMinHeight + 10.dp

private val TitleHeight = 120.dp

private val TitleMinOffset = HeaderMinHeight
private val TitleMaxOffset = IconMaxOffset + IconMaxSize

@Composable
fun CollapsedLayoutPage2() {
    Box(
        Modifier.fillMaxSize()
    ) {
        val gradientHeight = with(LocalDensity.current) {
            val height = (TitleMaxOffset + TitleHeight) - (TitleMinOffset + TitleHeight)
            height.roundToPx()
        }
        val scrollState = rememberScrollState()
        val fraction by derivedStateOf {
            (scrollState.value.toFloat() / gradientHeight).coerceIn(0f, 1f)
        }
        Header()
        BackButton()
        Body(scrollState)
        Title(fraction)
        Logo(fraction)
        BottomBar(Modifier.align(Alignment.BottomCenter))
    }
}


@Composable
private fun Header() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(HeaderMaxHeight)
            .background(Brush.horizontalGradient(jetTheme.pallet.interactivePrimary))
    ) {}
}

@Composable
private fun BackButton() {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .height(HeaderMinHeight)
            .padding(start = 12.dp),
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier.size(48.dp).background(Color.LightGray.copy(0.7f), CircleShape)
        ) {
            Icon(
                Icons.Default.ArrowBack,
                null,
                tint = jetTheme.pallet.primary
            )
        }
    }
}

@Composable
private fun Logo(fraction: Float) {
    ImageLayout(
        fraction = fraction,
        content = { Image() },
    )
}

@Composable
private fun Image() {
    Image(
        painterResource(R.drawable.placeholder),
        null,
        modifier = Modifier.fillMaxSize().clip(CircleShape),
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun ImageLayout(
    fraction: Float,
    content: ComposableFunction,
) {
    val iconMaxSize = with(LocalDensity.current) { IconMaxSize.roundToPx() }
    val iconMinSize = with(LocalDensity.current) { IconMinSize.roundToPx() }
    val iconMaxOffset = with(LocalDensity.current) { IconMaxOffset.roundToPx() }
    val iconMinOffset = with(LocalDensity.current) { IconMinOffset.roundToPx() }
    Layout(
        content = content,
        modifier = Modifier.fillMaxWidth().padding(IconMinOffset, 0.dp),
    ) { measurables, constraints ->
        check(measurables.size == 1)

        val iconSize = lerp(iconMaxSize, iconMinSize, fraction)
        val iconX = lerp(
            (constraints.maxWidth - iconMaxSize) / 2,
            constraints.maxWidth - iconMinSize,
            fraction
        )
        val iconY = lerp(iconMaxOffset, iconMinOffset, fraction)
        val placeable = measurables.first().measure(Constraints.fixed(iconSize, iconSize))
        layout(constraints.maxWidth, iconY + iconSize) {
            placeable.placeRelative(iconX, iconY)
        }
    }
}

@Composable
private fun Title(fraction: Float) {
    val offset = lerp(TitleMaxOffset, TitleMinOffset, fraction)
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Bottom),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = offset)
            .heightIn(TitleHeight)
            .background(Color.White)
            .padding(start = 16.dp),
    ) {
        Text(text = "Title", style = jetTheme.typography.h6, color = Color.Black)
        Text("subtitle", style = jetTheme.typography.body1, color = Color.Black.copy(0.7f))
        Text("pubtime: 2000-1-1", style = jetTheme.typography.body1, color = Color.Black.copy(0.7f))
        Divider()
    }
}

@Composable
private fun BottomBar(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(BottomBarHeight)
            .background(jetTheme.pallet.primary),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "bottom bar",
            style = jetTheme.typography.h6,
            color = Color.White,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Body(
    state: ScrollState,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(Modifier.height(HeaderMinHeight))
        Column(
            Modifier.fillMaxSize().verticalScroll(state)
        ) {
            Spacer(Modifier.height(IconMaxOffset + IconMaxSize + TitleHeight - HeaderMinHeight))
            Column(
                modifier = Modifier.fillMaxSize().background(Color.White)
            ) {
                repeat(50) {
                    ListItem(
                        modifier = Modifier.clickable { },
                        text = { Text("index: ${it + 1}") }
                    )
                }
                Spacer(Modifier.height(BottomBarHeight))
            }
        }
    }
}