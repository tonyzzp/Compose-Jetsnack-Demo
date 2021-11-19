package me.izzp.jetsnackdemo.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import me.izzp.jetsnackdemo.R
import me.izzp.jetsnackdemo.ui.theme.jetTheme
import me.izzp.jetsnackdemo.widget.CollapsedLayout

@Composable
fun CollapsedLayoutPage() {
    CollapsedLayout(
        header = { Header() },
        icon = { Icon() },
        title = { Title() },
        content = { Content() },
        bottom = { Bottom() }
    )
}


@Composable
private fun Header() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(jetTheme.pallet.primary),
    ) {
        Box(
            modifier = Modifier.height(56.dp),
            contentAlignment = Alignment.Center,
        ) {
            IconButton(
                onClick = {}
            ) {
                androidx.compose.material.Icon(
                    Icons.Default.ArrowBack,
                    null,
                    tint = jetTheme.pallet.secondary
                )
            }
        }
    }
}

@Composable
private fun Icon() {
    Image(
        painterResource(R.drawable.placeholder),
        null,
        modifier = Modifier
            .aspectRatio(1f)
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun Title() {
    Column(
        modifier = Modifier.padding(0.dp, 16.dp),
    ) {
        Text(
            text = "title",
            style = jetTheme.typography.h5,
            color = Color.Black,
            modifier = Modifier.padding(start = 16.dp, bottom = 6.dp)
        )
        Text(
            text = "subtitle",
            style = jetTheme.typography.subtitle1,
            color = Color.Black.copy(0.7f),
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "content content content",
            style = jetTheme.typography.caption,
            color = Color.Black.copy(0.7f),
            modifier = Modifier.padding(start = 16.dp, bottom = 6.dp)
        )
        Divider()
    }
}

@Composable
private fun Bottom() {
    Box(
        modifier = Modifier.fillMaxWidth().height(56.dp).background(jetTheme.pallet.primary),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Bottom Bar",
            style = jetTheme.typography.h6,
            color = Color.White,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Content() {
    Column(
        Modifier.fillMaxWidth().padding(14.dp, 0.dp)
    ) {
        println(LocalDensity.current.density)
        repeat(50) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                Text("box ${it + 1}")
            }
        }
    }
}