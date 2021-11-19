package me.izzp.jetsnackdemo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.Placeable

fun Placeable.dump() {
    println("width: $width, height: $height")
}

@Composable
fun rememberInt(value: Int = 0) = remember { mutableStateOf(value) }

@Composable
fun rememberFloat(value: Float = 0f) = remember { mutableStateOf(value) }

@Composable
fun rememberBoolean(value: Boolean = false) = remember { mutableStateOf(value) }


fun lerp(start: Float, end: Float, fraction: Float) = start + (end - start) * fraction


fun calcFraction(start: Int, end: Int, value: Int): Float =
    ((value.toFloat() - start) / (end - start)).coerceIn(0f, 1f)

fun calcFraction(start: Float, end: Float, value: Float): Float =
    ((value - start) / (end - start)).coerceIn(0f, 1f)

@Composable
fun <T> rememberDerivedStateOf(calculation: () -> T) = remember {
    derivedStateOf(calculation)
}

@Composable
fun <T> rememberDerivedStateOf(key1: Any?, calculation: () -> T) = remember(key1) {
    derivedStateOf(calculation)
}

@Composable
fun <T> rememberDerivedStateOf(key1: Any, key2: Any?, calculation: () -> T) = remember(key1, key2) {
    derivedStateOf(calculation)
}

@Composable
fun <T> rememberDerivedStateOf(key1: Any, key2: Any?, key3: Any?, calculation: () -> T) =
    remember(key1, key2, key3) {
        derivedStateOf(calculation)
    }

@Composable
fun <T> rememberDerivedStateOf(vararg keys: Any?, calculation: () -> T) = remember(*keys) {
    derivedStateOf(calculation)
}
