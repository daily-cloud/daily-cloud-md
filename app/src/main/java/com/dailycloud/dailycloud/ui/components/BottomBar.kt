package com.dailycloud.dailycloud.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dailycloud.dailycloud.R
import com.dailycloud.dailycloud.ui.navigation.NavigationItem
import com.dailycloud.dailycloud.ui.navigation.Screen
import com.dailycloud.dailycloud.ui.theme.DailyCloudTheme

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.home),
                icon = painterResource(R.drawable.home),
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.history),
                icon = painterResource(R.drawable.calendar),
                screen = Screen.History
            ),
            NavigationItem(
                title = stringResource(R.string.content),
                icon = painterResource(R.drawable.content),
                screen = Screen.Contents
            ),
            NavigationItem(
                title = stringResource(R.string.profile),
                icon = painterResource(R.drawable.user),
                screen = Screen.Profile
            ),
        )
        NavigationBar {
            navigationItems.map { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = item.icon,
                            contentDescription = item.title,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(Screen.Home.route) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    DailyCloudTheme {
        BottomBar(rememberNavController(), Modifier)
    }
}