package com.example.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OfflineBanner(
    isConnected: Boolean,
    modifier: Modifier = Modifier
) {
    if (!isConnected) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.error)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.WifiOff,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onError,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Offline - Menampilkan data cache",
                color = MaterialTheme.colorScheme.onError,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}