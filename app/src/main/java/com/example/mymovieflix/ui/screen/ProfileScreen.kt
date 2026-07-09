package com.example.mymovieflix.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.ui.component.BottomNavItem
import com.example.core.ui.component.NetflixBottomNavigation
import com.example.mymovieflix.ui.theme.MyMovieFlixTheme

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun ProfileScreenPreview() {
    MyMovieFlixTheme {
        ProfileScreen(onNavItemClick = {})
    }
}

@Composable
fun ProfileScreen(
    onNavItemClick: (BottomNavItem) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
        Text(
            text = "Profile",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Coming soon",
            color = Color.Gray,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(80.dp))
        }
        NetflixBottomNavigation(
            items = listOf(BottomNavItem.Home, BottomNavItem.Search, BottomNavItem.MyList, BottomNavItem.Profile),
            currentRoute = BottomNavItem.Profile.route,
            onItemClick = onNavItemClick
        )
    }
}
