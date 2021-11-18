package me.izzp.jetsnackdemo.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Stable
@Immutable
data class JetPallet(
    val primary: Color,
    val secondary: Color,

    val background: Color,
    val border: Color,
    val floated: Color,

    val interactivePrimary: List<Color>,
    val interactiveSecondary: List<Color>,
    val interactiveMask: List<Color>,

    val textPrimary: Color,
    val textSecondary: Color,
    val textHelp: Color,
    val textInteractive: Color,
    val textLink: Color,

    val iconPrimary: Color,
    val iconSecondary: Color,
    val iconInteractive: Color,
    val iconInactive: Color,

    val err: Color,
    val notification: Color,
)


val primary = Color(0xff4b30ed)
val primary2 = Color(0xff66ddff)
val secondary = Color(0xff86f7fa)
val secondary2 = Color(0xffaaf9fb)

fun lightPallet() = JetPallet(
    primary = primary,
    secondary = secondary,

    background = Color.White,
    border = Color.White.copy(0.12f),
    floated = Color(0xfff6f6f6),

    interactivePrimary = listOf(primary2, primary),
    interactiveSecondary = listOf(secondary2, secondary),
    interactiveMask = listOf(secondary2, primary, secondary2),

    textPrimary = primary,
    textSecondary = Color.Black.copy(0.87f),
    textHelp = Color.Black.copy(0.6f),
    textInteractive = Color.White,
    textLink = Color(0xff005687),

    iconPrimary = primary,
    iconSecondary = Color.Black.copy(0.87f),
    iconInactive = Color.White,
    iconInteractive = Color.White.copy(0.74f),

    err = Color(0xffd00036),
    notification = Color(0xffd00036)
)

fun darkPallet() = lightPallet().copy(
    background = Color(0xff212121),
    border = Color.Black.copy(0.12f),
    floated = Color(0xff2e2e2e),

    textSecondary = Color.White,
    textHelp = Color.White.copy(0.74f),
    textInteractive = Color.Black,
    textLink = secondary,

    iconSecondary = Color.White,
    iconInteractive = Color.Black.copy(0.87f),
    iconInactive = Color.Black.copy(0.6f),
)


val LocalJetPallet = staticCompositionLocalOf<JetPallet> {
    error("未提供 pallet")
}
