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
    val onBackground: Color,
    val onPrimary: Color,
    val onSecondary: Color,
    val gradientPrimary: List<Color>,
    val gradientSecondary: List<Color>,
)


val primary = Color(0xff4b30ed)
val primary2 = Color(0xff66ddff)
val secondary = Color(0xff86f7fa)
val secondary2 = Color(0xffaaf9fb)

fun lightPallet() = JetPallet(
    primary = primary,
    secondary = secondary,
    background = Color.White,
    onBackground = Color.Black,
    onPrimary = Color.White,
    onSecondary = Color.White,
    gradientPrimary = listOf(primary2, primary),
    gradientSecondary = listOf(secondary2, secondary),
)

fun darkPallet() = lightPallet().copy(
    background = Color.Black,
    onBackground = Color.White,
)


val LocalJetPallet = staticCompositionLocalOf<JetPallet> {
    error("未提供 pallet")
}
