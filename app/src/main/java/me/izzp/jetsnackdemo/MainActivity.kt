package me.izzp.jetsnackdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.izzp.jetsnackdemo.pages.CollapsedLayoutPage
import me.izzp.jetsnackdemo.pages.TabBarPage
import me.izzp.jetsnackdemo.ui.theme.JetTheme

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

@Composable
private fun Gate() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            val list = remember { listOf("collapsedLayout", "tabBar") }
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                list.forEach {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { navController.navigate(it) },
                    ) { Text(it) }
                }
            }
        }
        composable("collapsedLayout") { CollapsedLayoutPage() }
        composable("tabBar") { TabBarPage() }
    }

}
