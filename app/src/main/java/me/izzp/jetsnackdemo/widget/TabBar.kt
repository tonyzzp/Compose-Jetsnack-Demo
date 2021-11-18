package me.izzp.jetsnackdemo.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import me.izzp.jetsnackdemo.ComposableFunction
import me.izzp.jetsnackdemo.rememberInt
import me.izzp.jetsnackdemo.ui.theme.jetTheme


private val DefaultTabHeight = 56.dp

@Composable
fun JetTabBar(
    count: Int,
    selected: Int,
    content: ComposableFunction,
) {
    var itemWidth by rememberInt()
    val indication: ComposableFunction = remember {
        { JetIndication() }
    }
    val left by animateIntAsState(
        targetValue = selected * itemWidth,
        animationSpec = spring(Spring.DampingRatioMediumBouncy)
    )
    val right by animateIntAsState(
        targetValue = (selected + 2) * itemWidth,
        animationSpec = spring(Spring.DampingRatioMediumBouncy)
    )
    println("JetTabBar: itemWidth = $itemWidth, left = $left, right = $right")
    SubcomposeLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(DefaultTabHeight)
            .background(jetTheme.pallet.primary)
            .padding(4.dp, 8.dp)
    ) { constraints ->
        itemWidth = constraints.maxWidth / (count + 1)
        val placeables = subcompose("tabs", content).mapIndexed { index, measurable ->
            val width = if (index == selected) itemWidth * 2 else itemWidth
            measurable.measure(
                constraints.copy(
                    minWidth = width,
                    maxWidth = width,
                )
            )
        }
        val totalWidth = placeables.sumOf { it.width }
        val totalHeight = placeables.maxOf { it.height }
        val placeableIndication = subcompose("indication", indication).first().measure(
            Constraints.fixed(right - left, placeables[selected].height)
        )
        println("placeableIndication ${placeableIndication.width}*${placeableIndication.height}")

        layout(totalWidth, totalHeight) {
            println("layout")
            var x = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(x, (totalHeight - placeable.height) / 2)
                x += placeable.width
            }
            placeableIndication.placeRelative(left, (totalHeight - placeableIndication.height) / 2)
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun JetTabItem(
    icon: ImageVector,
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val alpha = if (selected) 1f else ContentAlpha.medium
        CompositionLocalProvider(
            LocalContentColor provides Color.White,
            LocalContentAlpha provides alpha,
        ) {
            Icon(icon, null)
            AnimatedVisibility(
                visible = selected,
                enter = expandHorizontally(),
                exit = shrinkHorizontally(),
            ) {
                Spacer(Modifier.width(4.dp))
                Text(
                    text = title,
                    style = jetTheme.typography.body1
                )
            }
        }
    }
}

@Composable
private fun JetIndication() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .border(1.5.dp, Color.White, CircleShape)
    ) {}
}