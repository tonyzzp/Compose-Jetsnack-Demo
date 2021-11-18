package me.izzp.jetsnackdemo.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import me.izzp.jetsnackdemo.ui.theme.jetTheme

@Composable
fun JetCheckableChip(
    text: String,
    checked: Boolean,
    onClick: () -> Unit,
) {
    val border = if (checked) {
        Modifier
    } else {
        Modifier.border(
            2.dp,
            Brush.horizontalGradient(jetTheme.pallet.interactivePrimary),
            CircleShape
        )
    }
    val bg = if (checked) {
        Modifier.background(jetTheme.pallet.secondary, CircleShape)
    } else {
        Modifier
    }
    Box(
        modifier = border
            .then(bg)
            .clip(CircleShape)
            .clickable(
                onClick = onClick,
                indication = rememberJetIndication(),
                interactionSource = remember { MutableInteractionSource() },
            )
            .padding(16.dp, 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text)
    }
}
