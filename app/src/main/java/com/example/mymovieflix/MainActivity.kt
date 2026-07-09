package com.example.mymovieflix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.core.ui.component.NetflixBottomNavigation
import com.example.core.ui.component.BottomNavItem
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mymovieflix.navigation.NavGraph
import com.example.mymovieflix.navigation.Screen
import com.example.mymovieflix.ui.theme.MyMovieFlixTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyMovieFlixTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route
                    val isSplash = currentRoute == Screen.Splash.route

                    Scaffold(
                        bottomBar = {
                            if (!isSplash) {
                                NetflixBottomNavigation(
                                    items = listOf(
                                        BottomNavItem.Home,
                                        BottomNavItem.Search,
                                        BottomNavItem.MyList,
                                        BottomNavItem.Profile
                                    ),
                                    currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route,
                                    onItemClick = { item ->
                                        navController.navigate(item.route) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    ) { innerPadding ->
                        NavGraph(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}