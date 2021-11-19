package me.izzp.jetsnackdemo.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        bottom = { Bottom() },
        modifier = Modifier.fillMaxSize().background(jetTheme.pallet.background)
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
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
    ) {
        CompositionLocalProvider(LocalContentColor provides jetTheme.pallet.onBackground) {
            Text(
                text = "title",
                style = jetTheme.typography.h5,
                modifier = Modifier.padding(start = 16.dp, bottom = 6.dp)
            )
            Text(
                text = "subtitle",
                style = jetTheme.typography.subtitle1,
                color = LocalContentColor.current.copy(0.7f),
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(
                text = "content content content",
                style = jetTheme.typography.caption,
                color = LocalContentColor.current.copy(0.7f),
                modifier = Modifier.padding(start = 16.dp, bottom = 6.dp)
            )
            Divider(color = jetTheme.pallet.onBackground.copy(0.7f))
        }
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
            color = jetTheme.pallet.onPrimary,
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
                Text("box ${it + 1}", color = jetTheme.pallet.onBackground)
            }
        }
    }
}