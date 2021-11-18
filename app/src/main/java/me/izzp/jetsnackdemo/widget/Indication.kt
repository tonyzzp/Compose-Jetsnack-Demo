package me.izzp.jetsnackdemo.widget

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import kotlinx.coroutines.flow.collect
import me.izzp.jetsnackdemo.ui.theme.jetTheme


@Composable
fun rememberJetIndication(colors: List<Color> = jetTheme.pallet.interactiveMask): Indication {
    return remember {
        object : Indication {
            @Composable
            override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
                val transparent = listOf(Color.Transparent, Color.Transparent)
                val state = remember(interactionSource) { mutableStateOf(transparent) }
                val instance = remember { JetIndicationInstance(state) }
                LaunchedEffect(interactionSource, colors) {
                    interactionSource.interactions.collect {
                        when (it) {
                            is PressInteraction.Press -> {
                                state.value = colors
                            }
                            else -> {
                                state.value = transparent
                            }
                        }
                    }
                }
                return instance
            }
        }
    }
}

private class JetIndicationInstance(val colors: State<List<Color>>) : IndicationInstance {

    override fun ContentDrawScope.drawIndication() {
        drawRect(
            brush = Brush.horizontalGradient(colors.value)
        )
        drawContent()
    }
}