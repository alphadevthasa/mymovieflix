package com.example.core.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
) {
    data object Home : BottomNavItem(
        route = "home",
        label = "Home",
        icon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home
    )
    data object Search : BottomNavItem(
        route = "search",
        label = "Search",
        icon = Icons.Outlined.Search,
        selectedIcon = Icons.Filled.Search
    )
    data object MyList : BottomNavItem(
        route = "my_list",
        label = "My List",
        icon = Icons.Outlined.BookmarkAdd,
        selectedIcon = Icons.Filled.BookmarkAdd
    )
    data object Profile : BottomNavItem(
        route = "profile",
        label = "Profile",
        icon = Icons.Outlined.Person,
        selectedIcon = Icons.Filled.Person
    )
}
