package me.izzp.jetsnackdemo.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp
import me.izzp.jetsnackdemo.ComposableFunction

object jetTheme {
    val pallet: JetPallet
        @Composable
        get() = LocalJetPallet.current

    val shapes: Shapes
        @Composable
        get() = LocalShapes.current

    val typography: Typography
        @Composable
        get() = LocalTypography.current
}


private val LocalShapes = staticCompositionLocalOf<Shapes> {
    error("")
}

private val LocalTypography = staticCompositionLocalOf<Typography> {
    error("")
}


@Composable
fun JetTheme(
    pallet: JetPallet = if (isSystemInDarkTheme()) darkPallet() else lightPallet(),
    shapes: Shapes = Shapes(
        small = RoundedCornerShape(8.dp),
        medium = RoundedCornerShape(16.dp),
        large = RoundedCornerShape(32.dp),
    ),
    typography: Typography = Typography(),
    content: ComposableFunction,
) {
    val ripple = rememberRipple()
    CompositionLocalProvider(
        LocalJetPallet provides pallet,
        LocalShapes provides shapes,
        LocalTypography provides typography,
        LocalIndication provides ripple,
        content = content,
    )
}