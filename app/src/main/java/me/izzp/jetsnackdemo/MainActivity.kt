package me.izzp.jetsnackdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.izzp.jetsnackdemo.pages.CollapsedLayoutPage
import me.izzp.jetsnackdemo.pages.CollapsedLayoutPage2
import me.izzp.jetsnackdemo.pages.TabBarPage
import me.izzp.jetsnackdemo.ui.theme.JetTheme
import me.izzp.jetsnackdemo.ui.theme.jetTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetTheme {
                Gate()
            }
        }
    }

}

private val list = listOf("tabBar", "collapsedLayout", "collapsedLayout2")

@Composable
private fun Gate() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(jetTheme.pallet.background)
                    .padding(8.dp, 16.dp),
            ) {
                list.forEach {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { navController.navigate(it) },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = jetTheme.pallet.primary,
                            contentColor = jetTheme.pallet.onPrimary,
                            disabledBackgroundColor = jetTheme.pallet.primary.copy(ContentAlpha.disabled),
                            disabledContentColor = jetTheme.pallet.onPrimary.copy(ContentAlpha.disabled),
                        )
                    ) { Text(it) }
                }
            }
        }
        composable("tabBar") { TabBarPage() }
        composable("collapsedLayout") { CollapsedLayoutPage() }
        composable("collapsedLayout2") { CollapsedLayoutPage2() }
    }

}
