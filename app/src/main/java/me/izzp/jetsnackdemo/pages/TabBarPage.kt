package me.izzp.jetsnackdemo.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import me.izzp.jetsnackdemo.rememberInt
import me.izzp.jetsnackdemo.widget.JetTabBar
import me.izzp.jetsnackdemo.widget.JetTabItem

@Composable
fun TabBarPage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
    ) {

        var currentTab by rememberInt()
        val tabs = remember {
            listOf(
                "HOME" to Icons.Default.Home,
                "SEARCH" to Icons.Default.Search,
                "MARKET" to Icons.Default.Shop,
                "PROFILE" to Icons.Default.People
            )
        }
        JetTabBar(
            count = 4,
            selected = currentTab,
        ) {
            tabs.forEachIndexed { index, tab ->
                JetTabItem(
                    icon = tab.second,
                    title = tab.first,
                    selected = currentTab == index,
                    onClick = {
                        currentTab = index
                    }
                )
            }
        }
    }
}