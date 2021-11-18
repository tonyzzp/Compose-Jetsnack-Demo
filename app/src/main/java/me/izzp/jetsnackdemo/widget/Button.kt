package me.izzp.jetsnackdemo.widget

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import me.izzp.jetsnackdemo.ComposableFunction
import me.izzp.jetsnackdemo.ui.theme.jetTheme

@Composable
fun JetButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColors: List<Color> = jetTheme.pallet.interactivePrimary,
    contentColor: Color = jetTheme.pallet.textInteractive,
    shape: Shape = CircleShape,
    indication: Indication = LocalIndication.current,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: ComposableFunction,
) {
    Row(
        modifier = modifier
            .background(Brush.horizontalGradient(backgroundColors), shape)
            .clip(shape)
            .clickable(
                interactionSource = interactionSource,
                indication = indication,
                onClick = onClick
            )
            .padding(12.dp, 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides contentColor,
            content = content
        )
    }
}

@Composable
fun JetOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    borderColors: List<Color> = jetTheme.pallet.interactivePrimary,
    contentColor: Color = jetTheme.pallet.secondary,
    pressedContentColor: Color = Color.Black,
    backgroundColors: List<Color> = jetTheme.pallet.interactiveSecondary,
    shape: Shape = CircleShape,
    content: ComposableFunction,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val indication = rememberJetIndication(backgroundColors)
    var color by remember { mutableStateOf(contentColor) }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect {
            when (it) {
                is PressInteraction.Press -> {
                    color = pressedContentColor
                }
                else -> {
                    color = contentColor
                }
            }
        }
    }
    Row(
        modifier = modifier
            .border(2.dp, Brush.horizontalGradient(borderColors), shape)
            .clip(shape)
            .clickable(
                interactionSource = interactionSource,
                indication = indication,
                onClick = onClick,
            )
            .padding(6.dp)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides color,
            content = content
        )
    }
}